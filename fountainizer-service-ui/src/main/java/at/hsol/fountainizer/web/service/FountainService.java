package at.hsol.fountainizer.web.service;

import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.parser.Parser;
import at.hsol.fountainizer.core.api.printer.ByteArrayPrinter;
import at.hsol.fountainizer.core.api.printer.StringPrinter;
import at.hsol.fountainizer.parser.FountainParserFactory;
import at.hsol.fountainizer.printer.html.HtmlOptions;
import at.hsol.fountainizer.printer.html.HtmlPrinterFactory;
import at.hsol.fountainizer.printer.pdf.PdfOptions;
import at.hsol.fountainizer.printer.pdf.PdfPrinterFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FountainService {

    public String createPreview(String fountainText) {
        Parser parser = FountainParserFactory.create(new PdfOptions());
        Content content = parser.parse(fountainText);
        StringPrinter printer = HtmlPrinterFactory.createStringPrinter(new HtmlOptions(true));

        return printer.printToString(content);
    }


    public byte[] createPdf(String fountainText) throws IOException {
        Parser parser = FountainParserFactory.create(new PdfOptions());
        Content content = parser.parse(fountainText);
        ByteArrayPrinter printer = PdfPrinterFactory.createByteArrayPrinter(new PdfOptions());

        return printer.printToBytes(content);
    }
}
