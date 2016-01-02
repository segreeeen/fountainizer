package at.hacksolutions.f2p.pdfbox.pager;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.pdfbox.paragraph.Paragraph;
import at.hacksolutions.f2p.pdfbox.paragraph.RichFormat;
import at.hacksolutions.f2p.pdfbox.paragraph.RichString;

public class StandardPager extends AbstractPager {

    private static final int FIRST = 1;
    private static final int SECOND = 2;

    public StandardPager(PDDocument doc, float top, float left, float right, float bottom) throws IOException {
	super(doc, top, left, right, bottom);
	initNextPage();
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
		x = getMarginLeft() + p.getMarginLeft() + ((p.getPageWidthRespectingMargins() - text.stringWidth(this)) / 2);
	    } else {
		x = getMarginLeft() + p.getMarginLeft();
	    }

	    float y = page.getMediaBox().getHeight() - getMarginTop() - writtenAreaY;

	    float currentLineWidth = 0.0f;

	    for (RichFormat rowPart : text.getFormattings()) {
		if (p.getLinetype() == LineType.TRANSITION) {
		    printRightAligned(rowPart, x, y, currentLineWidth);
		} else {
		    printLeftAligned(rowPart, x, y, currentLineWidth);
		}
		if (rowPart.isUnderline()) {
		    System.out.println("it's underlined.");
		    stream.moveTo(x + currentLineWidth, y + getUnderLineDifference());
		    stream.lineTo(x + currentLineWidth + rowPart.stringWidth(this), y + getUnderLineDifference());
		    stream.stroke();
		}
		currentLineWidth = currentLineWidth + rowPart.stringWidth(this);
	    }
	    if (p.isUnderlined()) {
		System.out.println("it's underlined.");
		stream.moveTo(x, y + getUnderLineDifference());
		stream.lineTo(x + text.stringWidth(this), y + getUnderLineDifference());
		stream.stroke();
	    }
	    writtenAreaY = writtenAreaY + getLineHeight();
	    stream.stroke();
	}
	writtenAreaY = writtenAreaY + p.getMarginBottom();
    }

    public void drawDualDialogue(LinkedList<Paragraph> d1, LinkedList<Paragraph> d2) throws IOException {
	remarginDialogue(d1, FIRST);
	remarginDialogue(d2, SECOND);
	float currentY = writtenAreaY;
	for (Paragraph p : d1) {
	    drawParagraph(p);
	}
	writtenAreaY = currentY;
	for (Paragraph p : d2) {
	    drawParagraph(p);
	}
    }

    private LinkedList<Paragraph> remarginDialogue(LinkedList<Paragraph> paras, int order) {
	if (order == FIRST) {
	    for (Paragraph p : paras) {
		p.setMarginLeft(getDualValue(p.getMarginLeft(), FIRST));
	    }
	} else if (order == SECOND) {
	    for (Paragraph p : paras) {
		p.setMarginLeft(getDualValue(p.getMarginLeft(), SECOND));
	    }
	}
	return paras;
    }

    private float getDualValue(Float f, int order) {
	if (order == FIRST) {
	    return f / 2;
	} else if (order == SECOND) {
	    return (getPageWidth() / 2) + (f / 2);
	}

	// not allowed to happen. just don't let that happen.
	return 0f;
    }
}
