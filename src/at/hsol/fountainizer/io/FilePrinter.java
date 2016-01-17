package at.hsol.fountainizer.io;

import java.io.IOException;
import java.net.URISyntaxException;
import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.parser.content.ParserContent;
import at.hsol.fountainizer.pdfbox.pager.CharacterPager;
import at.hsol.fountainizer.pdfbox.pager.PagerController;
import at.hsol.fountainizer.pdfbox.pager.StandardPager;
import at.hsol.fountainizer.pdfbox.pager.TitlePager;

/**
 * @author Felix Batusic
 */
public class FilePrinter {
    public static void writePDFBox(ParserContent dLines, String fileName, Options options) throws IOException, URISyntaxException {
	PagerController controller = new PagerController(80, 40, 60, 80, options);
	StandardPager pager = controller.getPager(PagerController.PagerType.STANDARD_PAGER);
	TitlePager titlePager = controller.getPager(PagerController.PagerType.TITLE_PAGER);
	CharacterPager cPager = controller.getPager(PagerController.PagerType.CHARACTER_PAGER);
	
	titlePager.printContent(dLines.getTitlepage());
	cPager.printContent(dLines.getCharacters().getCharacters(options));
	pager.printContent(dLines);
	
	controller.finalizeDocument(fileName);
    }
}
