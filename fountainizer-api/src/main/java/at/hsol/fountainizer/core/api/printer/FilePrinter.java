package at.hsol.fountainizer.core.api.printer;

import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.Options;

import java.io.IOException;

public interface FilePrinter {
    void setOptions(Options paramOptions);

    void printToFile(Content content, String fileName) throws IOException;
}
