package at.hsol.fountainizer.parser.types;

import at.hsol.fountainizer.parser.ParserConstants;
import at.hsol.fountainizer.parser.interfaces.MarginType;

/**
 * @author Felix Batusic
 */
public enum TitlePageType implements MarginType {
	// @formatter:off
    TITLE(false, false, false, 0.0F, 0F, 0.0F, 10.0F),
    CENTERED(false, true, false, 0F, 0F, 0F, 20.0F), 
    LEFT(false, false, false, 80F, 0F, 0.0F, 3.0F),
    RIGHT(false, false, false, 380F, 0F, 0.0F, 0.0F);
	// @formatter:on

	private final boolean uppercase;
	private final boolean centered;
	private final boolean underlined;
	private final float marginLeft;
	private final float marginRight;
	private final float marginTop;
	private final float marginBottom;

	private TitlePageType(boolean uppercase, boolean centered, boolean underlined, float marginLeft, float marginRight,
			float marginTop, float marginBottom) {
		this.uppercase = uppercase;
		this.centered = centered;
		this.underlined = underlined;
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
	}

	public TitlePageType getType(String s) {
		if (s.matches(ParserConstants.TP_CENTERED)) {
			return CENTERED;
		} else {
			return LEFT;
		}
	}

	@Override
	public boolean isUppercase() {
		return uppercase;
	}

	@Override
	public boolean isCentered() {
		return centered;
	}

	@Override
	public boolean isUnderlined() {
		return underlined;
	}

	@Override
	public float getMarginLeft() {
		return marginLeft;
	}

	@Override
	public float getMarginRight() {
		return marginRight;
	}

	@Override
	public float getMarginTop() {
		return marginTop;
	}

	@Override
	public float getMarginBottom() {
		return marginBottom;
	}

}
