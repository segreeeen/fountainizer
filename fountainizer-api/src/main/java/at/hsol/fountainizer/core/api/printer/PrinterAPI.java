package at.hsol.fountainizer.core.api.printer;

import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.Options;

import java.io.IOException;

public interface PrinterAPI {
    void setOptions(Options paramOptions);

    void print(Content paramContent, String paramString) throws IOException;
}
