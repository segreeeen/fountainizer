package at.hsol.fountainizer.core.api.printer;

import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.Options;

public interface StringPrinter {
    void setOptions(Options paramOptions);
    String printToString(Content content);
}
