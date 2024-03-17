package at.hsol.fountainizer.core.parser;

import at.hsol.fountainizer.core.parser.api.HeadingType;
import at.hsol.fountainizer.core.parser.api.Line;
import at.hsol.fountainizer.core.parser.api.LineType;

import static at.hsol.fountainizer.core.parser.ParserConstants.*;


/**
 * @author Felix Batusic
 */
class TypeHelper {
	private final ParserContent content;

	public TypeHelper(ParserContent content) {
		this.content = content;
	}

	LineType getType(ParserLine l) {
		if (isHeading(l)) {
			return LineType.HEADING;
		} else if (isParenthetical(l)) {
			return LineType.PARENTHETICAL;
		} else if (isLyrics(l)) {
			return LineType.LYRICS;
		} else if (isCentered(l)) {
			return LineType.CENTERED;
		} else if (isPagebreak(l)) {
			return LineType.PAGEBREAK;
		} else if (isTransition(l)) {
			return LineType.TRANSITION;
		} else if (isCharacter(l)) {
			return LineType.CHARACTER;
		} else if (isDialogue(l)) {
			return LineType.DIALOGUE;
		} else {
			return LineType.ACTION;
		}
	}

	private boolean isHeading(ParserLine l) {
		String text = l.getText();
		if (l.getPrev() != null) {
			if (text != null && l.getPrev().emptyText()) {
				return text.matches(L_HEADING);
			}
		}
		return false;
	}

	private HeadingType getHeading(ParserLine l) {
		String text = l.getText();
		if (text.matches(L_HEADING_INT)) {
			return HeadingType.INT;
		} else if (text.matches(L_HEADING_EXT)) {
			return HeadingType.EXT;
		} else if (text.matches(L_HEADING_EST)) {
			return HeadingType.EST;
		} else if (text.matches(L_HEADING_INT_EXT)) {
			return HeadingType.INT_EXT;
		} else if (text.matches(L_HEADING_CUSTOM)) {
			return HeadingType.CUSTOM;
		} else {
			return null;
		}
	}

	private boolean isCharacter(ParserLine l) {
		String text = l.getText();
		if (content.getStats().lookupCharacter(text) != null) {
			return true;
		}
		if (text.matches(L_CHARACTER)) {
			return true;
		} else {
			boolean isUpper = true;
			for (int i = 0; i < text.length(); i++) {
				if (Character.isLowerCase(text.charAt(i))) {
					isUpper = false;
					break;
				}
			}
			if (l.getPrev() != null && l.getNext() != null) {
				return isUpper && (l.getPrev().emptyText() && !l.getNext().emptyText());
			}
			return false;
		}
	}

	private boolean isDialogue(ParserLine l) {
		Line prevLine = l.getPrev();
		if (prevLine != null) {
			LineType prevType = prevLine.getLineType();
			if (l.getText() == null) {
				return prevType == LineType.DIALOGUE;
			} else
				return prevType == LineType.CHARACTER || prevType == LineType.PARENTHETICAL;
		}
		return false;
	}

	private boolean isParenthetical(ParserLine l) {
		String text = l.getText();
		return text != null && text.matches(L_PARENTHETICAL);

	}

	private boolean isTransition(ParserLine l) {
		String text = l.getText();
		if (l.getPrev() != null && l.getNext() != null) {
			if (l.getPrev().emptyText() && l.getNext().emptyText()) {
				return text.matches(L_TRANSITION_2) || text.matches(L_TRANSITION_1);
			}
		}
		return false;
	}

	private boolean isLyrics(ParserLine l) {
		String text = l.getText();
		if (text != null) {
			return text.startsWith(L_LYRICS);
		} else {
			return false;
		}
	}

	private boolean isCentered(ParserLine l) {
		String text = l.getText();
		return text != null && text.matches(L_CENTERED);
	}

	private boolean isPagebreak(ParserLine l) {
		String text = l.getText();
		if (text != null) {
			return text.matches(L_PAGEBREAK);
		}
		return false;
	}

}
