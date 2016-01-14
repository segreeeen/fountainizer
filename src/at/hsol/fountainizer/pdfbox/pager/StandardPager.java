package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

/**
 * @author Lukas Theis
 * @author Felix Batusic
 */
public class StandardPager extends AbstractPager {



    public StandardPager(PDDocument doc, float top, float left, float right, float bottom, Options options) throws IOException, URISyntaxException {
	super(doc, top, left, right, bottom, options);
	initNextPage();
    }

    @Override
    public void initNextPage() throws IOException {
	super.initNextPage();
	if (options != null && super.options.printPageNumbers()) {
	    printPageNr();
	}
    }

    public void drawDualDialogue(LinkedList<Paragraph> d1, LinkedList<Paragraph> d2) throws IOException {
	remarginDialogue(d1, FIRST);
	remarginDialogue(d2, SECOND);
	float currentY = writtenAreaY;
	for (Paragraph p : d1) {
	    printParagraph(p, false);
	}
	writtenAreaY = currentY;
	for (Paragraph p : d2) {
	    printParagraph(p, true);
	}
    }

    private LinkedList<Paragraph> remarginDialogue(LinkedList<Paragraph> paras, int order) {
	if (order == FIRST) {
	    for (Paragraph p : paras) {
		p.setMarginLeft(getDualValue(getMarginLeft()+p.getMarginLeft(), FIRST));
		p.setMarginRight((getPageWidth() / 2) + p.getMarginRight());
	    }
	} else if (order == SECOND) {
	    for (Paragraph p : paras) {
		p.setMarginLeft(getDualValue(getMarginLeft()+p.getMarginLeft(), SECOND));
	    }
	}
	return paras;
    }



    private void printPageNr() throws IOException {
	stream.beginText();
	stream.newLineAtOffset(getMarginLeft() + getPageWidth() / 2, getMarginBottom() / 2);
	stream.setFont(font, fontSize);
	stream.showText(String.valueOf(doc.getNumberOfPages()));
	stream.endText();
    }
}
