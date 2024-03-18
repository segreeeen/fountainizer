package at.hsol.fountainizer.printer.pdf;

import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.parser.Options;
import at.hsol.fountainizer.core.api.parser.Printer;
import at.hsol.fountainizer.printer.pdf.pager.CharacterPager;
import at.hsol.fountainizer.printer.pdf.pager.PagerController;
import at.hsol.fountainizer.printer.pdf.pager.StandardPager;
import at.hsol.fountainizer.printer.pdf.pager.TitlePager;

import java.io.IOException;

/**
 * @author Felix Batusic
 */
public class PDFPrinter implements Printer {

    Options options;


    private PDFPrinter() {}

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

    public static Printer create() {
        return new PDFPrinter();
    }
}
