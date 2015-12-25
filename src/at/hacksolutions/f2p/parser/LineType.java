package at.hacksolutions.f2p.parser;

public enum LineType {
    HEADING, CHARACTER, DIALOGUE, PARENTHETICAL, TRANSITION, ACTION, LYRICS, CENTERED, PAGEBREAK;

    public LineType getType(Line l, Lines outputLines) {

	return null;
    }

    private boolean isHeading(Line l, Lines outputLines) {
	String text = l.getOriginalText();
	if (text != null && outputLines.prevIsEmpty(l)) {
	    return text.matches("INT|EXT|EST|INT./EXT|INT/EXT|I/E|.");
	}
	return false;
    }

    private boolean isCharacter(Line l, Lines outputLines) {
	String text = l.getOriginalText();

	if (text.startsWith("@")) {
	    return true;
	} else {
	    boolean isLower = false;
	    for (int i = 0; i < text.length(); i++) {
		if (Character.isLowerCase(text.charAt(i))) {
		    isLower = true;
		    break;
		}
	    }

	    if (isLower && !outputLines.prevIsEmpty(l)
		    && outputLines.nextIsEmpty(l)) {
		return false;
	    } else {
		return true;
	    }
	}

    }

    private boolean isDialogue(Line l, Lines outputLines) {
	LineType prevType = outputLines.get(l.getLineNr() - 1).getLineType();
	if (l.getOriginalText() == null) {
	    if (prevType == LineType.DIALOGUE) {
		return true;
	    }
	} else if (prevType == LineType.CHARACTER
		|| prevType == LineType.PARENTHETICAL) {
	    return true;
	}
	return false;
    }

    private boolean isParenthetical(Line l) {
	String text = l.getOriginalText();
	if (text != null && text.matches("\\((.*?)\\)")) {
	    return true;
	}
	return false;

    }

    private boolean isTransition(Line l, Lines outputLines) {
	String text = l.getOriginalText();
	if (outputLines.prevIsEmpty(l) && outputLines.nextIsEmpty(l)) {
	    if (text.endsWith("TO:") || text.startsWith(">")) {
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

    private boolean isAction(Line l) {
	String text = l.getOriginalText();
	return text.startsWith("!");

    }

    private boolean isLyrics(Line l) {
	String text = l.getOriginalText();
	if (text != null) {
	    return text.startsWith("~");
	} else {
	    return false;
	}
    }

    private boolean isCentered(Line l) {
	String text = l.getOriginalText();
	if (text != null && text.matches("<(.*?)>")) {
	    return true;
	} else {
	    return false;
	}
    }

    private boolean isPagebreak(Line l) {
	String text = l.getOriginalText();
	if (text != null) {
	    return text.matches("={3,}+");
	}
	return false;
    }
}
