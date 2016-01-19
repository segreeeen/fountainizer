package at.hsol.fountainizer.pdfbox.pager;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

public class CustomScriptPager extends StandardPager {

    CustomScriptPager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
	super(controller, type);
    }
    
    @Override
    public void printParagraph(Paragraph p) throws IOException {
	setColor(null);
	
	// go to next line, if this is a newline
	if (p.getLinetype() == LineType.EMPTY) {
	    super.nextLine();
	    return;
	}
	

	// if this line is a character, we don't want the dialogue to be split
	// over two pages, so we check if there is enough space for at least 3
	// more lines. if there isn't we get next page
	if (p.getLinetype() == LineType.CHARACTER) {
		if (p.getRichText().getRawText().equals(controller.options.getCustomCharacter())) {
		    setColor(Color.RED);
		}
	    if (super.yExceeded(getLineHeight() * 3)) {
		nextPage();
	    }
	}

	if (super.yExceeded()) {
	    nextPage();
	}

	// take care of dual dialogues
	if (p.isDualDialogue()) {
	    super.setDualDialogue(p);
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

}
