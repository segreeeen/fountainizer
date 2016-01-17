package at.hsol.fountainizer.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserList;
import at.hsol.fountainizer.pdfbox.pager.CharacterPager;
import at.hsol.fountainizer.pdfbox.pager.PagerController;
import at.hsol.fountainizer.pdfbox.pager.StandardPager;
import at.hsol.fountainizer.pdfbox.pager.TitlePager;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

/**
 * @author Felix Batusic
 */
public class FilePrinter {
    public static void writePDFBox(ParserList dLines, String fileName, Options options) throws IOException, URISyntaxException {
	PagerController controller = new PagerController(40, 40, 60, 60, options);
	StandardPager pager = (StandardPager) controller.getPager(PagerController.PagerType.STANDARD_PAGER);
	TitlePager titlePager = (TitlePager) controller.getPager(PagerController.PagerType.TITLE_PAGER);
	CharacterPager cPager = (CharacterPager) controller.getPager(PagerController.PagerType.CHARACTER_PAGER);
	
	titlePager.printContent(dLines.getTitlepage());
	cPager.printContent(dLines.getCharacters().getCharacters(options));
	
	for (ParserLine line : dLines) {
	    LinkedList<Paragraph> paragraphs = line.getParagraphForPDF();
	    if (paragraphs != null) {
		for (Paragraph p : paragraphs) {
		    pager.printContent(p);
		}
	    }
	}
	
	controller.finalizeDocument(fileName);
    }
}
