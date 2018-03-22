package at.hsol.fountainizer.io;

import java.io.IOException;
import java.net.URISyntaxException;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.parser.content.ParserContent;
import at.hsol.fountainizer.parser.meta.Statistic;
import at.hsol.fountainizer.pdfbox.pager.CharacterPager;
//import at.hsol.fountainizer.pdfbox.pager.CustomScriptPager;
import at.hsol.fountainizer.pdfbox.pager.PagerController;
import at.hsol.fountainizer.pdfbox.pager.StandardPager;
import at.hsol.fountainizer.pdfbox.pager.TitlePager;

/**
 * @author Felix Batusic
 */
public class FilePrinter {

	Options options;

	public FilePrinter(Options options) {
		this.options = options;
	}

	public void writePDFBox(ParserContent dLines, String fileName, Statistic stats)
			throws IOException, URISyntaxException {
		PagerController controller = new PagerController(80, 40, 60, 80, options);
		StandardPager pager = controller.getPager(PagerController.PagerType.STANDARD_PAGER);
		// CustomScriptPager custompager =
		// controller.getPager(PagerController.PagerType.CUSTOM_PAGER);
		TitlePager titlePager = controller.getPager(PagerController.PagerType.TITLE_PAGER);
		CharacterPager cPager = controller.getPager(PagerController.PagerType.CHARACTER_PAGER);

		titlePager.printContent(dLines.getTitlepage());
		cPager.printContent(stats.getCharacters().getCharacters());
		pager.printContent(dLines);

		controller.finalizeDocument(fileName);
	}
}
