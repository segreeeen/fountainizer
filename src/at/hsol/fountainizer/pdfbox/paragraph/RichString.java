package at.hsol.fountainizer.pdfbox.paragraph;

import java.io.IOException;
import java.util.LinkedList;

import at.hsol.fountainizer.pdfbox.pager.AbstractPager;

/**
 * @author Lukas Theis
 */
public class RichString {

	private LinkedList<RichFormat> formattings;
	private final String rawText;

	public LinkedList<RichFormat> getFormattings() {
		return formattings;
	}

	public RichString(String rawText) {
		this(rawText, null);
	}

	private RichString(LinkedList<RichFormat> formattings) {
		this.formattings = formattings;
		this.rawText = null;
	}

	public RichString(String rawText, RichFormat singleFormatting) {
		formattings = new LinkedList<>();
		this.rawText = rawText;

		if (singleFormatting == null) {
			RichFormatParser rfp = new RichFormatParser(rawText);
			formattings = rfp.parse();
		} else {
			singleFormatting.setText(rawText);
			formattings.add(singleFormatting);
		}

	}

	public RichString substring(int beginIndex, int endIndex) {
		LinkedList<RichFormat> formattings = new LinkedList<>();

		int i = 0;

		RichFormat currentFormat = this.formattings.getFirst();
		String currentString = currentFormat.getText();
		String newString = "";
		while (i < endIndex) {
			newString = "";
			if (i + currentString.length() < beginIndex) {
				// do nothing
			} else if (i + currentString.length() < endIndex) {
				if (i < beginIndex) {
					newString = (currentString.substring(beginIndex - i));
				} else {
					newString = (currentString);
				}
				formattings.add(currentFormat.cloneWithNewText(newString));
			} else if (i + currentString.length() >= endIndex) {
				if (i < beginIndex) {
					newString = (currentString.substring(beginIndex - i, endIndex - i));
				} else {
					newString = (currentString.substring(0, endIndex - i));
				}
				formattings.add(currentFormat.cloneWithNewText(newString));
			}
			i = i + currentString.length();
			if (currentFormat != this.formattings.getLast()) {
				currentFormat = this.formattings.get(this.formattings.indexOf(currentFormat) + 1);
				currentString = currentFormat.getText();

			} else {
				i = endIndex;
			}

		}

		return new RichString(formattings);
	}

	@Override
	public String toString() {
		String res = "";
		for (RichFormat string : formattings) {
			res = res + string.getText();
		}
		return res;
	}

	public void convertToUpperCase() {
		for (RichFormat string : this.formattings) {
			string.setText(string.getText().toUpperCase());
		}
	}

	public float stringWidth(AbstractPager<?> page) throws IOException {
		float width = 0.0f;
		for (RichFormat text : formattings) {
			width = width + text.stringWidth(page);
		}
		return width;
	}

	public String getRawText() {
		return rawText;
	}

}