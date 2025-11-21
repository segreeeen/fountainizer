package at.hsol.fountainizer.printer.html;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.printer.FilePrinter;
import at.hsol.fountainizer.core.api.printer.StringPrinter;

public class HtmlPrinterFactory {
    public static FilePrinter createFilePrinter(Options options) {
        return new HtmlPrinter((HtmlOptions) options);
    }

    public static StringPrinter createStringPrinter(Options options) {
        return new HtmlPrinter((HtmlOptions) options);
    }
}
