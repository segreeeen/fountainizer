package at.hacksolutions.f2p.pdfbox.pager;

import java.awt.Color;
import java.io.IOException;

import at.hacksolutions.f2p.parser.types.TitleLineType;
import at.hacksolutions.f2p.pdfbox.paragraph.Paragraph;
import at.hacksolutions.f2p.pdfbox.paragraph.RichFormat;
import at.hacksolutions.f2p.pdfbox.paragraph.RichString;

public class TitlePager extends AbstractPager {
    private float centerOffset;
    private float lowerLeftOffset;

    public TitlePager(StandardPager p) throws IOException {
	super(p.getDoc(), p.getMarginTop(), p.getMarginLeft(), p.getMarginRight(), p.getMarginBottom());
	super.page = p.page;
	super.stream = p.stream;
	super.fontSize = p.fontSize;
	super.writtenAreaY = page.getMediaBox().getHeight();
	centerOffset = (getPageHeight()-100);
	lowerLeftOffset = (getPageHeight() / 4);
    }
    

    @Override
    public void drawParagraph(Paragraph p) throws IOException {
	p.initForPager(this);
	centerOffset += p.getMarginTop();
	lowerLeftOffset += p.getMarginTop();
	for (RichString text : p.getLines()) {
	    float x;
	    float y;
	    if (p.getLinetype() == TitleLineType.CENTERED) {
		x = getMarginLeft() + p.getMarginLeft()
			+ ((p.getPageWidthRespectingMargins()
				- text.stringWidth(this)) / 2);
		y = centerOffset;
		centerOffset -= getLineHeight();
	    } else if (p.getLinetype() == TitleLineType.LEFT) {
		x = getMarginLeft() + p.getMarginLeft();
		y = lowerLeftOffset;
		lowerLeftOffset -= getLineHeight();
	    } else {
		x = getMarginLeft() + p.getMarginLeft();
		y = page.getMediaBox().getHeight() - getMarginTop()
			- writtenAreaY;
	    }

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
	    centerOffset -= getLineHeight();
	    lowerLeftOffset -= getLineHeight();

	    stream.stroke();

	}
	centerOffset += p.getMarginBottom();
	lowerLeftOffset += p.getMarginBottom();
    }

}