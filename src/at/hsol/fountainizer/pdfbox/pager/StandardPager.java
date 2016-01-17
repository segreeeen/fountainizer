package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import at.hsol.fountainizer.parser.content.DynamicLines;
import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

public class StandardPager extends AbstractPager<DynamicLines> {
    private enum Dual {
	FIRST, SECOND;
    }

    private Integer fontSize;
    private Dual currentDual = null;
    private float originalY;

    StandardPager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
	super(controller);
	super.type = type;
	this.fontSize = null;
    }

    @Override
    public void printContent(DynamicLines t) throws IOException {
	for (ParserLine line : t) {
	    LinkedList<Paragraph> paragraphs = line.getParagraphForPDF();
	    if (paragraphs != null) {
		for (Paragraph p : paragraphs) {
		    printParagraph(p);
		}
	    }
	}
    }

    public void printParagraph(Paragraph p) throws IOException {
	// go to next line, if this is a newline
	if (p.getLinetype() == LineType.EMPTY) {
	    super.nextLine();
	    return;
	}

	// if this line is a character, we don't want the dialogue to be split
	// over two pages, so we check if there is enough space for at least 3
	// more lines. if there isn't we get next page
	if (p.getLinetype() == LineType.CHARACTER) {
	    if (super.yExceeded(getLineHeight() * 3)) {
		nextPage();
	    }
	}

	if (super.yExceeded()) {
	    nextPage();
	}

	// take care of dual dialogues
	if (p.isDualDialogue()) {
	    setDualDialogue(p);
	    remarginDual(p);
	}

	// initializes a list of lines with strings fitting the width of the
	// actualPageWidth()
	p.initForPager(this);

	List<RichString> lines = p.getLines();

	for (RichString rs : lines) {

	    if (p.isCentered()) { // print centered
		super.xPos = (getMarginLeft() + p.getMarginLeft() + ((p.getActualPageWidth() - rs.stringWidth(this)) / 2));
	    }

	    if (p.getLinetype() == LineType.TRANSITION) {
		super.xPos = (((p.getActualPageWidth() - rs.stringWidth(this) - p.getMarginRight()) / 2));
	    }

	    List<RichFormat> formats = rs.getFormattings();
	    float currentLineWidth = 0f;
	    for (RichFormat rf : formats) {
		if (rf.isUnderline()) { // underline line if underlined
		    printLeftAligned(rf, super.xPos + currentLineWidth + p.getMarginLeft(), super.yPos, true);
		} else {
		    printLeftAligned(rf, super.xPos + currentLineWidth + p.getMarginLeft(), super.yPos);
		}
		currentLineWidth += rf.stringWidth(this);
	    }
	    nextLine();
	}
	super.finishLine(p.getMarginBottom()); // finish paragraph

    }

    private void setDualDialogue(Paragraph p) {
	if (p.getLinetype() == LineType.CHARACTER) {
	    if (currentDual == null) {
		originalY = yPos;
		currentDual = Dual.FIRST;
	    } else if (currentDual == Dual.FIRST) {
		yPos = originalY;
		originalY = 0;
		currentDual = Dual.SECOND;
	    }
	}
    }

    private Paragraph remarginDual(Paragraph p) {
	if (currentDual == Dual.FIRST) {
	    p.setMarginLeft(getDualValue(getMarginLeft() + p.getMarginLeft()));
	    p.setMarginRight(getPageWidth() / 2);
	} else if (currentDual == Dual.SECOND) {
	    p.setMarginLeft(getDualValue(getMarginLeft() + p.getMarginLeft()));
	    p.setMarginRight(p.getActualPageWidth());
	}
	return p;
    }

    private float getDualValue(Float f) {
	if (currentDual == Dual.FIRST) {
	    return (f / 2);
	} else if (currentDual == Dual.SECOND) {
	    return (getPageWidth() / 2) + (f / 2);
	} else {
	    throw new IllegalStateException("\nIt's not really possible to get here.\nHow am I supposed to help you?!\nI have nooo Idea what you did.");
	}
    }

    @Override
    public int getFontSize() {
	if (fontSize != null) {
	    return fontSize;
	} else {
	    return PagerController.STANDARD_FONT_SIZE;
	}
    }

    private void printLeftAligned(RichFormat rowPart, float x, float y) throws IOException {
	super.printLeftAligned(rowPart.getText(), x, y, rowPart.selectFont(this), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
    }

    private void printLeftAligned(RichFormat rowPart, float x, float y, boolean underlined) throws IOException {
	super.printLeftAligned(rowPart.getText(), x, y, rowPart.selectFont(this), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	super.underline(x, y + super.getUnderLineDifference(), x + rowPart.stringWidth(this), y + super.getUnderLineDifference());
    }

    @SuppressWarnings("unused")
    private void printRightAligned(RichFormat rowPart, float x, float y) throws IOException {
	super.printRightAligned(rowPart.getText(), rowPart.stringWidth(this), x, y, rowPart.selectFont(this), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
    }

}
