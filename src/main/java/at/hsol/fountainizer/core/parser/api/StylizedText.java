package at.hsol.fountainizer.core.parser.api;

import java.util.List;

public interface StylizedText {
    List<? extends StylizedTextPart> getStylizedTextParts();

    StylizedText substring(int beginIndex, int endIndex);

    void convertToUpperCase();

    String getRawText();
}
