package at.hsol.fountainizer.parser.types;

import at.hsol.fountainizer.parser.interfaces.ParserType;

/**
 * @author Felix Batusic
 */
public enum LineType implements ParserType {
    HEADING(true, false, false, 40.0F, 0F, 15.0F, 0.0F), 
    CHARACTER(true, false, false, 40.0F, 0F, 0.0F, 0.0F), 
    DIALOGUE(false, false, false, 80.0F, 40F, 0.2F, 0.0F), 
    DUAL_DIALOGUE(false, false, false, 80.0F, 40F, 0.2F, 0.0F), 
    PARENTHETICAL(false, false, false, 80.0F, 0F, 0.2F, 0.0F), 
    TRANSITION(false,false, false, 400.0F, 0F, 0.0F, 0.0F), 
    ACTION(false, false, false, 40.0F, 40F, 10F, 0F), 
    LYRICS(false, true, false, 10.0F, 0F, 10.0F, 10.0F), 
    CENTERED(false, true, false, 10.0F, 0F, 10.0F, 10.0F), 
    PAGEBREAK(false, false, false, 0F, 0F, 0F, 0F), 
    TITLE(false, false, false, 0F, 0F, 0F, 0F), 
    LINENUMBER(false, false, false, 20F, 0F, 0F, 0F), 
    EMPTY(false, false, false, 0F, 0F, 0F, 0F),;

    private final boolean uppercase;
    private final boolean centered;
    private final boolean underlined;
    private final float marginLeft;
    private final float marginRight;
    private final float marginTop;
    private final float marginBottom;

    private LineType(boolean uppercase, boolean centered, boolean underlined, float marginLeft, float marginRight, float marginTop, float marginBottom) {
	this.uppercase = uppercase;
	this.centered = centered;
	this.underlined = underlined;
	this.marginLeft = marginLeft;
	this.marginRight = marginRight;
	this.marginTop = marginTop;
	this.marginBottom = marginBottom;
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

    public float getMarginTop() {
	return marginTop;
    }

    public float getMarginBottom() {
	return marginBottom;
    }

    public float getMarginRight() {
	return marginRight;
    }

}
