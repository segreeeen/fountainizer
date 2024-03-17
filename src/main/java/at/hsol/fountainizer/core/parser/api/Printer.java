package at.hsol.fountainizer.core.parser.api;

import at.hsol.fountainizer.Options;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Printer {
    void setOptions(Options options);

    void print(Content textlines, String fileOut) throws IOException;
}
