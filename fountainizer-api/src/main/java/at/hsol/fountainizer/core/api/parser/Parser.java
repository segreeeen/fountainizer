package at.hsol.fountainizer.core.api.parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface Parser {
    double read() throws IOException;

    double parse() throws IllegalStateException;

    double print() throws IOException, URISyntaxException;

    int numOfLines();

    Statistics getStats();

    List<? extends CharacterInfo> getCharacterStats();
}
