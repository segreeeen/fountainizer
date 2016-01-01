package at.hacksolutions.f2p.pdfbox;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import at.hacksolutions.f2p.parser.types.LineType;

public class StandardPager extends AbstractPager {
    public StandardPager(PDDocument doc, float top, float left, float right,
	    float bottom) throws IOException {
	super(doc, top, left, right, bottom);
    }

    public void drawParagraph(Paragraph p) throws IOException {
	p.initForPager(this);
	writtenAreaY = writtenAreaY + p.getMarginTop();
	for (RichString text : p.getLines()) {

	    if (writtenAreaY + getLineHeight() > getPageHeight()) {
		initNextPage();
	    }

	    float x;
	    if (p.isCentered()) {
		x = getMarginLeft() + p.getMarginLeft()
			+ ((p.getPageWidthRespectingMargins()
				- text.stringWidth(this)) / 2);
	    } else {
		x = getMarginLeft() + p.getMarginLeft();
	    }
	    float y = page.getMediaBox().getHeight() - getMarginTop()
		    - writtenAreaY;

	    float currentLineWidth = 0.0f;

	    for (RichFormat rowPart : text.getFormattings()) {
		if (p.getLinetype() == LineType.TRANSITION) {
		    printRightAligned(rowPart, x, y, currentLineWidth);
		} else {
		    printLeftAligned(rowPart, x, y, currentLineWidth);
		}
		if (rowPart.isUnderline()) {
		    stream.addLine(x + currentLineWidth, y + getUnderLineDifference(),
			    x + currentLineWidth + rowPart.stringWidth(this),
			    y + getUnderLineDifference());
		}

		currentLineWidth = currentLineWidth + rowPart.stringWidth(this);

	    }

	    if (p.isUnderlined()) {
		stream.addLine(x, y + getUnderLineDifference(),
			x + text.stringWidth(this),
			y + getUnderLineDifference());
	    }
	    writtenAreaY = writtenAreaY + getLineHeight();

	    stream.stroke();

	}
	writtenAreaY = writtenAreaY + p.getMarginBottom();
    }
}
