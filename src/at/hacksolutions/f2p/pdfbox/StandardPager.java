package at.hacksolutions.f2p.pdfbox;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;

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

	    float xOfRow = 0.0f;

	    for (RichFormat rowPart : text.getFormattings()) {

		stream.beginText();
		stream.setNonStrokingColor(Color.BLACK);

		stream.setFont(rowPart.selectFont(this), fontSize);

		stream.moveTextPositionByAmount(x + xOfRow, y);
		stream.drawString(rowPart.getText());
		stream.endText();
		if (rowPart.isUnderline()) {
		    stream.addLine(x + xOfRow, y + getUnderLineDifference(),
			    x + xOfRow + rowPart.stringWidth(this),
			    y + getUnderLineDifference());
		}

		xOfRow = xOfRow + rowPart.stringWidth(this);

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
