package at.hsol.fountainizer.printer.pdf;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.printer.PrinterAPI;
import at.hsol.fountainizer.printer.pdf.pager.CharacterPager;
import at.hsol.fountainizer.printer.pdf.pager.PagerController;
import at.hsol.fountainizer.printer.pdf.pager.StandardPager;
import at.hsol.fountainizer.printer.pdf.pager.TitlePager;
import java.io.IOException;

public class PdfPrinter implements PrinterAPI {
    Options options;

    PdfPrinter(Options options) {
        this.options = options;
        if (options == null)
            this.options = new Options();
    }

    public void setOptions(Options options) {
        this.options = options;
        if (options == null)
            this.options = new Options();
    }

    public void print(Content content, String fileName) throws IOException {
        PagerController controller = new PagerController(80.0F, 40.0F, 60.0F, 80.0F, this.options);
        StandardPager pager = (StandardPager)controller.getPager(PagerController.PagerType.STANDARD_PAGER);
        TitlePager titlePager = (TitlePager)controller.getPager(PagerController.PagerType.TITLE_PAGER);
        CharacterPager cPager = (CharacterPager)controller.getPager(PagerController.PagerType.CHARACTER_PAGER);
        if (this.options.printTitlePage())
            titlePager.printContent(content.getTitlepageLines());
        if (this.options.printCharacterPage())
            cPager.printContent(content.getStats().getCharacters());
        pager.printContent(content);
        controller.finalizeDocument(fileName);
    }
}
