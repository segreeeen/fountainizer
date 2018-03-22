package at.hsol.fountainizer.parser.types;

import at.hsol.fountainizer.parser.interfaces.MarginType;

public enum HeadingType implements MarginType {
	INT, EXT, EST, INT_EXT, CUSTOM;

	@Override
	public boolean isUppercase() {
		return LineType.HEADING.isUppercase();
	}

	@Override
	public boolean isCentered() {
		return LineType.HEADING.isCentered();
	}

	@Override
	public boolean isUnderlined() {
		return LineType.HEADING.isUnderlined();
	}

	@Override
	public float getMarginLeft() {
		return LineType.HEADING.getMarginLeft();
	}

	@Override
	public float getMarginRight() {
		return LineType.HEADING.getMarginRight();
	}

	@Override
	public float getMarginTop() {
		return LineType.HEADING.getMarginTop();
	}

	@Override
	public float getMarginBottom() {
		return LineType.HEADING.getMarginBottom();
	}

}
