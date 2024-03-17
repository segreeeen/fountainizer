package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.parser.Options;
import at.hsol.fountainizer.core.api.parser.Parser;
import at.hsol.fountainizer.core.api.parser.Printer;

public class ParserFactory {
    public static Parser create(String fileIn, String fileOut, Printer printer, Options options) {
        return new ParserController(fileIn, fileOut, printer, options);
    }
}
