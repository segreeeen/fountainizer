package at.hsol.fountainizer.core.api.parser;
import java.io.IOException;

public interface Printer {
    void setOptions(Options options);

    void print(Content textlines, String fileOut) throws IOException;
}
