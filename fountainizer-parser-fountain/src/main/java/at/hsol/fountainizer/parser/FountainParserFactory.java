package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.Parser;

public class FountainParserFactory {

    public static Parser create(Options options) {
        return new FountainParser(options);
    }
}
