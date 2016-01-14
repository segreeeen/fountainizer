package at.hsol.fountainizer.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.parser.data.FCharacter;
import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserList;
import at.hsol.fountainizer.parser.line.SimpleLine;
import at.hsol.fountainizer.parser.line.TitlePage;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.pager.StandardPager;
import at.hsol.fountainizer.pdfbox.pager.TitlePager;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

/**
 * @author Felix Batusic
 */
public class FilePrinter {

    public static void writePDFBox(ParserList dLines, String filename, Options options) throws IOException, URISyntaxException {
	PDDocument doc = new PDDocument();
	StandardPager standardPage = new StandardPager(doc, 60, 40, 40, 60, options);
	TitlePager titlePage = new TitlePager(standardPage);

	TitlePage tp = dLines.getTitlepage();
	if (!tp.emptyText()) {
	    LinkedList<Paragraph> paragraphs = tp.getParagraphForPDF();
	    if (paragraphs != null) {
		for (Paragraph p : paragraphs) {
		    if (p.getLinetype() != null) {
		    }
		    titlePage.printParagraph(p);
		}
	    }
	    standardPage.initNextPage();
	}
	
	if (options.printCharacterPage()) {
	    List<FCharacter> characters = dLines.getCharacters().getCharacters(options);
	    LinkedList<RichString> formattedCharacters = new LinkedList<>();
	    LinkedList<Paragraph> paragraphs = new LinkedList<>();
		for (FCharacter c: characters) {
		    RichString s = new RichString(c.getName() + ":             " + c.getTakes());
		    formattedCharacters.add(s);
		}
		for (RichString s: formattedCharacters) {
		    Paragraph p = new Paragraph(s);
		    paragraphs.add(p);
		}
		for(Paragraph p: paragraphs) {
		    p.setCentered(true);
		    standardPage.printParagraph(p);
		}
		standardPage.initNextPage();
	}

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
			standardPage.printParagraph(p);
		    }
		}
	    }
	}
	titlePage.finalize(filename);
	standardPage.finalize(filename);
    }
}
