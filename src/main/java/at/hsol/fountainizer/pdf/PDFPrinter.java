package at.hsol.fountainizer.pdf;

import java.io.IOException;
import java.net.URISyntaxException;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.core.parser.api.Content;
import at.hsol.fountainizer.core.parser.api.Printer;
import at.hsol.fountainizer.core.parser.api.Statistics;
import at.hsol.fountainizer.pdf.pager.PagerController;
import at.hsol.fountainizer.pdf.pager.TitlePager;
import at.hsol.fountainizer.pdf.pager.CharacterPager;
import at.hsol.fountainizer.pdf.pager.StandardPager;

/**
 * @author Felix Batusic
 */
public class PDFPrinter implements Printer {

    Options options;

    @Override
    public void setOptions(Options options) {
        this.options = options;
    }

    @Override
    public void print(Content content, String fileName) throws IOException {
        PagerController controller = new PagerController(80, 40, 60, 80, options);
        StandardPager pager = controller.getPager(PagerController.PagerType.STANDARD_PAGER);
        TitlePager titlePager = controller.getPager(PagerController.PagerType.TITLE_PAGER);
        CharacterPager cPager = controller.getPager(PagerController.PagerType.CHARACTER_PAGER);
        if (options.printTitlePage()) {
            titlePager.printContent(content.getTitlepageLines());
        }
        if (options.printCharacterPage()) {
            cPager.printContent(content.getStats().getCharacters());
        }
        pager.printContent(content);

        controller.finalizeDocument(fileName);
    }
}
