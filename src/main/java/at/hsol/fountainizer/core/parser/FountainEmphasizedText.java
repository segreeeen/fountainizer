package at.hsol.fountainizer.core.parser;

import at.hsol.fountainizer.core.parser.api.StylizedText;
import at.hsol.fountainizer.core.parser.api.StylizedTextPart;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author Lukas Theis
 */
public class FountainEmphasizedText implements StylizedText {

	private List<FountainEmphasizedTextPart> formattings;
	private final String rawText;

	@Override
	public List<? extends StylizedTextPart> getStylizedTextParts() {
		return formattings;
	}

	private FountainEmphasizedText(LinkedList<FountainEmphasizedTextPart> formattings) {
		this.formattings = formattings;
		this.rawText = null;
	}

	FountainEmphasizedText(String rawText, FountainEmphasizedTextPart formattedText) {
		formattings = new LinkedList<>();
		this.rawText = rawText;

		if (formattedText == null) {
			formattings = parse(rawText);
		} else {
			formattedText.setText(rawText);
			formattings.add(formattedText);
		}

	}

	@Override
	public FountainEmphasizedText substring(int beginIndex, int endIndex) {
		LinkedList<FountainEmphasizedTextPart> formattings = new LinkedList<>();

		int i = 0;

		FountainEmphasizedTextPart currentFormat = this.formattings.getFirst();
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

		return new FountainEmphasizedText(formattings);
	}

	LinkedList<FountainEmphasizedTextPart> parse(String rawText) {
		LinkedList<FountainEmphasizedTextPart> formats = new LinkedList<>();

		char[] text = rawText.toCharArray();

		// the only rule we are going to follow is, that what has been activated first has to be deactivated first,
		// therefore we want a LIFO stack to push the starting format and if we detect a format again, we
		// compare if it is the one that should be closed.
		// if not -> exception,
		// if it is -> remove from stack
		Stack<Style> styleQueue = new Stack<>();

		StringBuilder currentStyledTextBuilder = new StringBuilder();

		// going to use while so we can skip positions easier
		int pos = 0;
		while (pos < text.length) {
			char currentChar = text[pos];
			switch (currentChar) {
				case '_', '[', '*' -> {
					Style nextStyle = checkStyle(pos, text); // may be Italic, Bold or BoldItalic here
					boolean addStyle = false; //set this if style is not closing or no style is opened
					String currentStyledText = currentStyledTextBuilder.toString();
					if (!styleQueue.isEmpty()) {
						Style currentStyle = styleQueue.peek();
						if (currentStyle == nextStyle) {
							pos += nextStyle.skip;
							if (currentStyledText.length() > 0) {
								formats.add(new FountainEmphasizedTextPart(currentStyledText, new LinkedList<>(styleQueue)));
							}
							currentStyledTextBuilder.setLength(0);
							styleQueue.pop();
						} else {
							addStyle = true;
						}
					} else {
						addStyle = true;
					}
					if (addStyle) {
						pos += nextStyle.skip;
						if (currentStyledText.length() > 0) {
							formats.add(new FountainEmphasizedTextPart(currentStyledText, new LinkedList<>(styleQueue)));
						}
						currentStyledTextBuilder.setLength(0);
						styleQueue.push(nextStyle);
					}
				}
				default -> currentStyledTextBuilder.append(currentChar);
			}

			pos++;
		}

		formats.add(new FountainEmphasizedTextPart(currentStyledTextBuilder.toString(), new LinkedList<>(styleQueue)));

		return formats;
	}


	private Style checkStyle(int pos, char[] array) {
		char atPos0 = safeGet(pos, array);
		char atPos1 = safeGet(pos + 1, array);
		char atPos2 = safeGet(pos + 2, array);

		Style style = Style.get("" + atPos0 + atPos1 + atPos2);
		if (style == Style.NONE) {
			style = Style.get("" + atPos0 + atPos1);
		}

		if (style == Style.NONE) {
			style = Style.get("" + atPos0);
		}

		return style;
	}

	private char safeGet(int pos, char[] array) {
		if (pos >= 0 && pos < array.length) {
			return array[pos];
		}
		return '\0'; // or any other default value or action
	}

	enum Style {
		ITALIC(0), BOLD(1), BITALIC(2), UNDERLINED(0), COMMENT(1), NONE(0);

		final int skip;

		Style(int val) {
			this.skip = val;
		}

		static Style get(String styleCode) {
			return switch (styleCode) {
				case "*" -> ITALIC;
				case "**" -> BOLD;
				case "***" -> BITALIC;
				case "_" -> UNDERLINED;
				case "[[" -> COMMENT;
				default -> NONE;
			};
		}
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		for (FountainEmphasizedTextPart string : formattings) {
			res.append(string.getText());
		}
		return res.toString();
	}

	@Override
	public void convertToUpperCase() {
		for (FountainEmphasizedTextPart string : this.formattings) {
			string.setText(string.getText().toUpperCase());
		}
	}

	@Override
	public String getRawText() {
		return rawText;
	}

}