package at.hsol.fountainizer.core.api.printer;

import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.Options;

import java.io.IOException;

public interface ByteArrayPrinter {
    void setOptions(Options paramOptions);
    byte[] printToBytes(Content content) throws IOException;
}
