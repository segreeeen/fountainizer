package at.hsol.fountainizer.core.api.parser;

public interface StylizedTextPart {

    boolean isUnderline();

    boolean isBold();

    boolean isItalic();

    String getText();
}
