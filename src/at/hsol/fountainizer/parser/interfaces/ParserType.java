package at.hsol.fountainizer.parser.interfaces;

/**
 * @author Felix Batusic
 */
public interface ParserType {

    public boolean isUppercase();

    public boolean isCentered();

    public boolean isUnderlined();

    public float getMarginLeft();

    public float getMarginRight();

    public float getMarginTop();

    public float getMarginBottom();

}
