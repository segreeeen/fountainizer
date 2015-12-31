package at.hacksolutions.f2p.parser.types;

public enum LineType implements ParserType{
    HEADING(true, false, false, 40.0F, 0F, 15.0F, 15.0F), 
    CHARACTER(true, false, false, 250.0F, 0F, 15F, 0.0F), 
    DIALOGUE(false, false, false, 150.0F, 70F, 0.2F, 0.0F), 
    PARENTHETICAL(false, false, false, 200.0F, 0F, 0.2F, 0.0F), 
    TRANSITION(false, false, false, 10.0F, 0F, 10.0F, 10.0F),  
    ACTION(false, false, false, 40.0F, 0F, 5.0F, 0.2F), 
    LYRICS(false, false, false, 10.0F, 0F, 10.0F, 10.0F),  
    CENTERED(false, true, false, 10.0F, 0F, 10.0F, 10.0F), 
    PAGEBREAK(false, false, false, 0F, 0F, 0F, 0F),  
    TITLE(false, false, false, 0F, 0F, 0F, 0F),
    EMPTY(false, false, false, 0F, 0F, 0F, 0F), ;

    private final boolean uppercase;
    private final boolean centered;
    private final boolean underlined;
    private final float marginLeft;
    private final float marginRight;
    private final float marginTop;
    private final float marginBottom;
    
    private LineType(boolean uppercase, boolean centered, boolean underlined, 
	    float marginLeft, float marginRight, float marginTop, float marginBottom) {
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
