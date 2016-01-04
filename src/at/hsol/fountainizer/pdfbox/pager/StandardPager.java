package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.pdfbox.pdmodel.PDDocument;

import at.hsol.fountainizer.parser.interfaces.ParserType;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

/**
 * @author Lukas Theis
 * @author Felix Batusic
 */
public class StandardPager extends AbstractPager {

    private static final int FIRST = 1;
    private static final int SECOND = 2;
    private ParserType prevType;

    public StandardPager(PDDocument doc, float top, float left, float right, float bottom) throws IOException, URISyntaxException {
	super(doc, top, left, right, bottom);
	prevType = LineType.EMPTY;
	initNextPage();
    }

    public void drawParagraph(Paragraph p) throws IOException {
	if (prevType == LineType.ACTION && p.getLinetype() == LineType.EMPTY) {
	    prevType = p.getLinetype();
	    return;
	} else if (prevType == LineType.EMPTY && p.getLinetype() == LineType.ACTION) {
	    writtenAreaY = (writtenAreaY + getLineHeight());
	    prevType = p.getLinetype();
	} else if (p.getLinetype() == LineType.EMPTY) {
	    prevType = p.getLinetype();
	    return;
	} 
	
	p.initForPager(this);
	writtenAreaY = (writtenAreaY + p.getMarginTop()) - 2;
	for (RichString text : p.getLines()) {
	    if (writtenAreaY + (getLineHeight() * 2) > getPageHeight()) {
		initNextPage();
	    }

	    float x;
	    if (p.isCentered()) {
		x = getMarginLeft() + p.getMarginLeft() + ((p.getPageWidthRespectingMargins() - text.stringWidth(this)) / 2);
	    } else if (p.getLinetype() == LineType.TRANSITION) {
		x = getMarginRight();
	    } else {
		x = getMarginLeft() + p.getMarginLeft();
	    }

	    float y = page.getMediaBox().getHeight() - getMarginTop() - writtenAreaY;

	    float currentLineWidth = 0.0f;
	    if (p.getLinetype() == LineType.TRANSITION) {
		LinkedList<RichFormat> trformats = text.getFormattings();
		ListIterator<RichFormat> li = trformats.listIterator(trformats.size());

		// Iterate in reverse.
		while (li.hasPrevious()) {
		    RichFormat f = li.previous();
		    printRightAligned(f, this.getPageWidth() - x, y, currentLineWidth);
		}

	    } else {
		for (RichFormat rowPart : text.getFormattings()) {
		    printLeftAligned(rowPart, x, y, currentLineWidth);
		    if (rowPart.isUnderline()) {
			stream.setLineWidth(0.1f);
			stream.moveTo(x + currentLineWidth, y + getUnderLineDifference());
			stream.lineTo(x + currentLineWidth + rowPart.stringWidth(this), y + getUnderLineDifference());
			stream.stroke();
		    }
		    currentLineWidth = currentLineWidth + rowPart.stringWidth(this);
		}
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
	writtenAreaY = (writtenAreaY + p.getMarginBottom()) - 2;
	if (p.getLinetype() != null) {
	    prevType = p.getLinetype();
	}
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
