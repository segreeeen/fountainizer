package at.hsol.fountainizer.printer.pdf;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.printer.ByteArrayPrinter;
import at.hsol.fountainizer.core.api.printer.FilePrinter;

public class PdfPrinterFactory {
    public static FilePrinter createFilePrinter(Options options) {
        return new PdfPrinter((PdfOptions) options);
    }

    public static ByteArrayPrinter createByteArrayPrinter(Options options) {
        return new PdfPrinter((PdfOptions) options);
    }
}
