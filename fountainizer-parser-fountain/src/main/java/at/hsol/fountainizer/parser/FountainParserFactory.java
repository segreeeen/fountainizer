package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.ParserAPI;

public class FountainParserFactory {

    public static ParserAPI create(Options options) {
        return new FountainParser(options);
    }
}
