package at.hacksolutions.f2p.parser;

import static at.hacksolutions.f2p.parser.ParserConstants.*;

public enum LineType {
    HEADING, CHARACTER, DIALOGUE, PARENTHETICAL, TRANSITION, ACTION, LYRICS, CENTERED, PAGEBREAK;

    public static LineType getType(Line l, Lines outputLines) {
	if (isHeading(l, outputLines)) {
	    return LineType.HEADING;
	} else if (isCharacter(l, outputLines)) {
	    return LineType.CHARACTER;
	} else if (isDialogue(l, outputLines)) {
	    return LineType.DIALOGUE;
	} else if (isParenthetical(l)) {
	    return LineType.PARENTHETICAL;
	} else if (isTransition(l, outputLines)) {
	    return LineType.TRANSITION;
	} else if (isLyrics(l)) {
	    return LineType.LYRICS;
	} else if (isCentered(l)) {
	    return LineType.CENTERED;
	} else if (isPagebreak(l)) {
	    return LineType.PAGEBREAK;
	} else {
	    return LineType.ACTION;
	}

    }

    private static boolean isHeading(Line l, Lines outputLines) {
	String text = l.getText();
	if (text != null && outputLines.prevIsEmpty(l)) {
	    return text.matches(L_HEADING);
	}
	return false;
    }

    private static boolean isCharacter(Line l, Lines outputLines) {
	String text = l.getText();

	if (text.matches(L_CHARACTER)) {
	    return true;
	} else {
	    boolean isUpper = false;
	    for (int i = 0; i < text.length(); i++) {
		if (Character.isLowerCase(text.charAt(i))) {
		    isUpper = true;
		    break;
		}
	    }

	    if (isUpper && (!outputLines.prevIsEmpty(l)
		    && outputLines.nextIsEmpty(l))) {
		return false;
	    } else {
		return true;
	    }
	}

    }

    private static boolean isDialogue(Line l, Lines outputLines) {
	LineType prevType = outputLines.get(l.getLineNr() - 1).getLineType();
	if (l.getText() == null) {
	    if (prevType == LineType.DIALOGUE) {
		return true;
	    }
	} else if (prevType == LineType.CHARACTER
		|| prevType == LineType.PARENTHETICAL) {
	    return true;
	}
	return false;
    }

    private static boolean isParenthetical(Line l) {
	String text = l.getText();
	if (text != null && text.matches(L_PARENTHETICAL)) {
	    return true;
	}
	return false;

    }

    private static boolean isTransition(Line l, Lines outputLines) {
	String text = l.getText();
	if (outputLines.prevIsEmpty(l) && outputLines.nextIsEmpty(l)) {
	    if (text.matches(L_TRANSITION_1) || text.matches(L_TRANSITION_2)) {
		for (int i = 0; i < text.length(); i++) {
		    if (Character.isLowerCase(text.charAt(i))) {
			return false;
		    }
		}
		return true;
	    }
	}
	return false;

    }

    private static boolean isLyrics(Line l) {
	String text = l.getText();
	if (text != null) {
	    return text.startsWith(L_LYRICS);
	} else {
	    return false;
	}
    }

    private static boolean isCentered(Line l) {
	String text = l.getText();
	if (text != null && text.matches(L_CENTERED)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isPagebreak(Line l) {
	String text = l.getText();
	if (text != null) {
	    return text.matches(L_PAGEBREAK);
	}
	return false;
    }
}
