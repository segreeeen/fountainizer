package at.hsol.fountainizer.printer.pdf;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.printer.PrinterAPI;

public class PdfPrinterFactory {
    public static PrinterAPI create(Options options) {
        return new PdfPrinter(options);
    }
}
