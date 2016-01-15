package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.util.List;

import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

public class StandardPager extends AbstractPager<Paragraph> {
    private enum Dual {
	FIRST, SECOND;
    }

    private Integer fontSize;
    private Dual currentDual = null;
    private float originalY;

    StandardPager(PagerController controller) throws IOException {
	super(controller);
	super.type = PagerController.PagerType.STANDARD_PAGER;
	this.fontSize = null;
    }

    @Override
    public void printContent(Paragraph p) throws IOException {
	if (p.getLinetype() == LineType.CHARACTER) {
	    if (super.yExceeded(getLineHeight() * 3)) {
		nextPage();
	    }
	}

	if (super.yExceeded()) {
	    nextPage();
	}

	if (p.getLinetype() == LineType.EMPTY) {
	    super.nextLine();
	    return;
	}

	p.initForPager(this);

	if (p.isDualDialogue()) {
	    setDualDialogue(p);
	    remarginDual(p);
	}

	List<RichString> lines = p.getLines();
	nextLine(p.getMarginTop()); // start paragraph
	for (RichString rs : lines) {
	    if (p.isCentered()) {
		super.xPos = (getMarginLeft() + p.getMarginLeft() + ((p.getActualPageWidth() - rs.stringWidth(this)) / 2));
	    }
	    List<RichFormat> formats = rs.getFormattings();
	    for (RichFormat rf : formats) {
		if (xExceeded(rf.stringWidth(this))) {
		    if (p.isCentered()) {
			nextLine(getMarginLeft() + p.getMarginLeft() + ((p.getActualPageWidth() - rs.stringWidth(this)) / 2));
		    } else {
			nextLine();
		    }
		}
		printLeftAligned(rf, super.xPos + p.getMarginLeft(), super.yPos);
		super.xPos += rf.stringWidth(this);
	    }
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
		p.setMarginLeft(getDualValue(getMarginLeft()+p.getMarginLeft()));
		p.setMarginRight((getPageWidth() / 2) + p.getMarginRight());
	} else if (currentDual == Dual.SECOND) {
		p.setMarginLeft(getDualValue(getMarginLeft()+p.getMarginLeft()));
	}
	return p;
    }
    
    private float getDualValue(Float f) {
	if (currentDual == Dual.FIRST) {
	    return (f / 2);
	} else if (currentDual == Dual.SECOND) {
	    return (getPageWidth() / 2) + (f / 2);
	}

	// not allowed to happen. just don't let that happen.
	return 0f;
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

    @SuppressWarnings("unused")
    private void printRightAligned(RichFormat rowPart, float x, float y) throws IOException {
	super.printRightAligned(rowPart.getText(), rowPart.stringWidth(this), x, y, rowPart.selectFont(this), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
    }

}
