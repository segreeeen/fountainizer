package at.hsol.fountainizer.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;

import org.apache.pdfbox.pdmodel.PDDocument;

import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserLines;
import at.hsol.fountainizer.parser.line.SimpleLine;
import at.hsol.fountainizer.parser.line.TitlePage;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.pager.StandardPager;
import at.hsol.fountainizer.pdfbox.pager.TitlePager;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

/**
 * @author Felix Batusic
 */
public class FilePrinter {

    public static void writePDFBox(ParserLines dLines, String filename) throws IOException, URISyntaxException {
	PDDocument doc = new PDDocument();
	StandardPager standardPage = new StandardPager(doc, 60, 40, 40, 60);
	TitlePager titlePage = new TitlePager(standardPage);

	TitlePage tp = dLines.getTitlepage();
	if (!tp.emptyText()) {
	    LinkedList<Paragraph> paragraphs = tp.getParagraphForPDF();
	    if (paragraphs != null) {
		for (Paragraph p : paragraphs) {
		    if (p.getLinetype() != null) {
		    }
		    titlePage.drawParagraph(p);
		}
	    }
	}

	standardPage.initNextPage();

	LinkedList<Paragraph> firstDialogue = null;
	LinkedList<Paragraph> secondDialogue = null;
	for (ParserLine line : dLines) {
	    if (line.getLineType() == LineType.PAGEBREAK) {
		standardPage.initNextPage();
		continue;
	    } else if (((SimpleLine) line).isDualDialogue()) {
		if (firstDialogue == null) {
		    firstDialogue = line.getParagraphForPDF();
		} else if (firstDialogue != null && secondDialogue == null) {
		    if (line.getLineType() != LineType.CHARACTER) {
			LinkedList<Paragraph> paragraphs = line.getParagraphForPDF();
			if (paragraphs != null) {
			    firstDialogue.addAll(line.getParagraphForPDF());
			}
		    } else {
			secondDialogue = line.getParagraphForPDF();
		    }
		} else if (firstDialogue != null && secondDialogue != null) {
		    if (line.getLineType() != LineType.EMPTY) {
			LinkedList<Paragraph> paragraphs = line.getParagraphForPDF();
			if (paragraphs != null) {
			    secondDialogue.addAll(line.getParagraphForPDF());
			}
		    } else {
			// if there is a dual Dialogue, it's guarantueed, that
			// it ends here.
			standardPage.drawDualDialogue(firstDialogue, secondDialogue);
			firstDialogue = null;
			secondDialogue = null;
		    }
		}
	    } else {
		LinkedList<Paragraph> paragraphs = line.getParagraphForPDF();
		if (paragraphs != null) {
		    for (Paragraph p : paragraphs) {
			standardPage.drawParagraph(p);
		    }
		}
	    }
	}
	titlePage.finalize(filename);
	standardPage.finalize(filename);
    }
}