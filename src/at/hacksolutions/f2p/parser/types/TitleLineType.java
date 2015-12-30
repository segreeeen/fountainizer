package at.hacksolutions.f2p.parser.types;

public enum TitleLineType implements ParserType{
    TITLE(true, false, false, 40.0F, 0F, 15.0F, 15.0F),
    CREDIT(true, false, false, 40.0F, 0F, 15.0F, 15.0F),
    AUTHOR(true, false, false, 40.0F, 0F, 15.0F, 15.0F),
    SOURCE(true, false, false, 40.0F, 0F, 15.0F, 15.0F),
    DATE(true, false, false, 40.0F, 0F, 15.0F, 15.0F),
    CONTACT(true, false, false, 40.0F, 0F, 15.0F, 15.0F);

    private final boolean uppercase;
    private final boolean centered;
    private final boolean underlined;
    private final float marginLeft;
    private final float marginRight;
    private final float marginTop;
    private final float marginBottom;
    
    private TitleLineType(boolean uppercase, boolean centered, boolean underlined, 
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
