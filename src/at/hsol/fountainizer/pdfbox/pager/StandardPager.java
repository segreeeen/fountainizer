package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.apache.pdfbox.pdmodel.PDDocument;

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

    public StandardPager(PDDocument doc, float top, float left, float right, float bottom) throws IOException, URISyntaxException {
	super(doc, top, left, right, bottom);
	initNextPage();
    }

    @Override
    public void initNextPage() throws IOException {
	super.initNextPage();
	printPageNr();
    }

    public void drawParagraph(Paragraph p) throws IOException {
	if (p.getLinetype() == LineType.EMPTY) {
	    writtenAreaY = (writtenAreaY + getLineHeight());
	    return;
	}

	p.initForPager(this);
	writtenAreaY = (writtenAreaY + p.getMarginTop()) - 2;
	List<RichString> lines = p.getLines();
	// float linesHeight = getLineHeight()*(p.getLines().size());
	for (RichString text : lines) {
	    if (writtenAreaY + getLineHeight() > getPageHeight()) {
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
		    Integer ltn = p.getLineTypeNumber();
		    if (ltn != null && p.getLinetype() == LineType.CHARACTER) {
			super.printLineTypeNumber(y, ltn);
		    }
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
		stream.moveTo(x, y + getUnderLineDifference());
		stream.lineTo(x + text.stringWidth(this), y + getUnderLineDifference());
		stream.stroke();
	    }
	    writtenAreaY = writtenAreaY + getLineHeight();
	    stream.stroke();
	}
	writtenAreaY = (writtenAreaY + p.getMarginBottom()) - 2;

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
		p.setMarginRight((getPageWidth() / 2) + p.getMarginRight());
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

    private void printPageNr() throws IOException {
	stream.beginText();
	stream.newLineAtOffset(getMarginLeft() + getPageWidth() / 2, getMarginBottom() / 2);
	stream.setFont(font, fontSize);
	stream.showText(String.valueOf(doc.getNumberOfPages()));
	stream.endText();
    }
}
