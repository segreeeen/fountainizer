package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import at.hsol.fountainizer.parser.content.ParserContent;
import at.hsol.fountainizer.parser.interfaces.Line;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

public class StandardPager extends AbstractPager<ParserContent> {
    private enum Dual {
	FIRST, SECOND;
    }

    private Integer fontSize;
    protected Dual currentDual = null;
    protected Float nextY = null;
    protected Float originalY;

    StandardPager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
	super(controller);
	super.type = type;
	this.fontSize = null;
    }

    void nextLine(Paragraph p) throws IOException {
	if (controller.options.printTakeNumber() && p.getLinetype() == LineType.CHARACTER) {
	    printTakeNumber(p.getLineTypeNumber());
	}
	super.nextLine();
    }

    @Override
    public void printContent(ParserContent t) throws IOException {
	for (Line line : t) {
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
	    
	    if (p.getLinetype() == LineType.PAGEBREAK) {
		super.nextPage();
		continue;
	    }

	    if (p.isCentered()) { // print centered
		super.xPos = (super.getAbsoluteWidth() / 2) - (rs.stringWidth(this)/2)-p.getMarginLeft();
	    }

	    if (p.getLinetype() == LineType.TRANSITION) {
		super.xPos = (p.getActualPageWidth() - rs.stringWidth(this) - p.getMarginRight());
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
	    // return yPos to position if left (first) dialogue was longer than
	    // right (second)
	    if (nextY != null && p.getLinetype() == LineType.DIALOGUE && nextY < yPos) {
		yPos = nextY;
		nextY = null;
		currentDual = null;
		originalY = null;
	    } else {
		nextLine(p);
	    }
	}

	super.finishLine(p.getMarginBottom()); // finish paragraph

    }
    
    @Override
    public int getFontSize() {
	if (fontSize != null) {
	    return fontSize;
	} else {
	    return PagerController.STANDARD_FONT_SIZE;
	}
    }

    protected void setDualDialogue(Paragraph p) {
	if (p.getLinetype() == LineType.CHARACTER) {
	    if (currentDual == null) {
		originalY = super.yPos;
		currentDual = Dual.FIRST;
	    } else if (currentDual == Dual.FIRST) {
		nextY = yPos;
		super.yPos = originalY;
		originalY = null;
		currentDual = Dual.SECOND;
	    }
	}
    }

    protected Paragraph remarginDual(Paragraph p) {
	if (currentDual == Dual.FIRST) {
	    p.setMarginLeft(getDualValue(getMarginLeft() + p.getMarginLeft()) - 40);
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

    protected void printLeftAligned(RichFormat rowPart, float x, float y) throws IOException {
	super.printString(rowPart.getText(), x, y, rowPart.selectFont(this), getFontSize(), getColor());
    }

    protected void printLeftAligned(RichFormat rowPart, float x, float y, boolean underlined) throws IOException {
	super.printString(rowPart.getText(), x, y, rowPart.selectFont(this), getFontSize(), getColor());
	super.underline(x, y + super.getUnderLineDifference(), x + rowPart.stringWidth(this), y + super.getUnderLineDifference());
    }

    private void printTakeNumber(Integer lineNr) throws IOException {
	if (currentDual != null && currentDual == Dual.SECOND) {
	    float nrWidth = getFont().getStringWidth(lineNr.toString()) / 1000 * getFontSize();
	    printString(lineNr.toString(), getMarginLeft() + (getPageWidth() / 2) + 30 - nrWidth, yPos, getFont(), getFontSize(), getColor());
	} else {
	    float nrWidth = getFont().getStringWidth(lineNr.toString()) / 1000 * getFontSize();
	    printString(lineNr.toString(), getMarginLeft() - nrWidth - 10, yPos, getFont(), getFontSize(), getColor());
	}
    }

}
