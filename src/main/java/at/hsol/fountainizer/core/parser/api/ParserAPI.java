package at.hsol.fountainizer.core.parser.api;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.core.parser.ParserController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ParserAPI {
    static ParserAPI create(String source, String dest, Printer pdfPrinter, Options options) {
        return new ParserController(source, dest, pdfPrinter, options);
    }

    double read() throws IOException;

    double parse() throws IllegalStateException;

    double print() throws IOException, URISyntaxException;

    int numOfLines();

    Statistics getStats();

    List<? extends CharacterInfo> getCharacterStats();
}
