package at.hacksolutions.f2p.parser;

public class Parser {
    private Lines outputLines;

    public Parser(Lines inputLines, boolean[] emptyLines) {
	this.outputLines = inputLines;
    }

    public Lines parse() {
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    Line l = outputLines.get(i);
	    setAttributes(l);
	}
	return outputLines;
    }

    private void setAttributes(Line l) {
	// TODO Auto-generated method stub

    }

    private LineType getType(Line l) {

	return null;
    }

    private boolean isHeading(Line l) {
	String text = l.getOriginalText();
	if (text != null && outputLines.prevIsEmpty(l)) {
	    return text.matches("INT|EXT|EST|INT./EXT|INT/EXT|I/E|.");
	}
	return false;
    }

    private boolean isCharacter(Line l) {
	String text = l.getOriginalText();

	if (text.startsWith("@")) {
	    return true;
	}

	for (int i = 0; i < text.length(); i++) {
	    if (Character.isLowerCase(text.charAt(i))) {
		return false;
	    }
	}

	if (!outputLines.prevIsEmpty(l) && outputLines.nextIsEmpty(l)) {
	    return false;
	}

	return true;

    }

    private boolean isDialogue(Line l) {
	LineType prevType = outputLines.get(l.getLineNr()-1).getLineType();
	if (l.getOriginalText() == null) {
	    if (prevType == LineType.DIALOGUE) {
		return true;
	    }
	} else if ( prevType == LineType.CHARACTER || prevType == LineType.PARENTHETICAL) {
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

    private boolean isTransition(Line l) {
	return false;

    }

    private boolean isAction(Line l) {
	return false;

    }

    private boolean isLyrics() {
	return false;

    }

    private boolean isCentered() {
	return false;

    }

    private boolean isPagebreak() {
	return false;

    }

}
