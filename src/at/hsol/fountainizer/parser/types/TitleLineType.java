package at.hsol.fountainizer.parser.types;

import at.hsol.fountainizer.parser.interfaces.ParserType;

/**
 * @author Felix Batusic
 */
public enum TitleLineType implements ParserType {
    CENTERED(false, true, false, 0F, 0F, 0F, 0.0F), 
    LEFT(false, false, false, 0.0F, 0F, 0.0F, 10.0F);

    private final boolean uppercase;
    private final boolean centered;
    private final boolean underlined;
    private final float marginLeft;
    private final float marginRight;
    private final float marginTop;
    private final float marginBottom;

    private TitleLineType(boolean uppercase, boolean centered,
	    boolean underlined, float marginLeft, float marginRight,
	    float marginTop, float marginBottom) {
	this.uppercase = uppercase;
	this.centered = centered;
	this.underlined = underlined;
	this.marginLeft = marginLeft;
	this.marginRight = marginRight;
	this.marginTop = marginTop;
	this.marginBottom = marginBottom;
    }

    public TitleLineType getType(String s) {
	if (s.matches(ParserConstants.TP_CENTERED)) {
	    return CENTERED;
	} else {
	    return LEFT;
	}
    }

    public boolean isUppercase() {
	return uppercase;
    }

    public boolean isCentered() {
	return centered;
    }

    public boolean isUnderlined() {
	return underlined;
    }

    public float getMarginLeft() {
	return marginLeft;
    }

    public float getMarginRight() {
	return marginRight;
    }

    public float getMarginTop() {
	return marginTop;
    }

    public float getMarginBottom() {
	return marginBottom;
    }

}
