package at.hsol.fountainizer.core.api.parser;

import java.util.List;

public interface StylizedText {
    List<? extends StylizedTextPart> getStylizedTextParts();

    StylizedText substring(int beginIndex, int endIndex);

    void convertToUpperCase();

    String getRawText();
}
