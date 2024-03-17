package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.parser.StylizedTextPart;

import java.util.List;

/**
 * @author Lukas Theis
 */
class FountainEmphasizedTextPart implements StylizedTextPart {

	private boolean underline = false;
	private boolean bold = false;
	private boolean italic = false;
	private String text = "";

	FountainEmphasizedTextPart(boolean underline, boolean bold, boolean italic) {
		setUnderline(underline);
		setBold(bold);
		setItalic(italic);
	}

	FountainEmphasizedTextPart(String text, List<FountainEmphasizedText.Style> styles) {
		this.text = text;
		styles.forEach(s -> {
			switch (s) {
				case ITALIC -> setItalic(true);
				case BOLD -> setBold(true);
				case BITALIC -> {
					setBold(true);
					setItalic(true);
				}
				case UNDERLINED -> setUnderline(true);
			}
		});
	}

	FountainEmphasizedTextPart cloneWithNewText(String text) {
		FountainEmphasizedTextPart clone = new FountainEmphasizedTextPart(underline, bold, italic);
		clone.setText(text);
		return clone;

	}
	void setUnderline(boolean underline) {
		this.underline = underline;
	}

	@Override
	public boolean isUnderline() {
		return underline;
	}
	@Override
	public boolean isBold() {
		return bold;
	}

	@Override
	public boolean isItalic() {
		return italic;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text + ", Bold = " + bold + ", Italic = " + italic + ", Underlined = " + underline + "\n";
	}

	void setBold(boolean bold) {
		this.bold = bold;
	}

	void setItalic(boolean italic) {
		this.italic = italic;
	}

	void setText(String text) {
		this.text = text;
	}
}