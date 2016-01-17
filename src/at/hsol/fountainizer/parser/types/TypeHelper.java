package at.hsol.fountainizer.parser.types;

import static at.hsol.fountainizer.parser.types.ParserConstants.L_CENTERED;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_CHARACTER;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_HEADING;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_LYRICS;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_PAGEBREAK;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_PARENTHETICAL;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_TRANSITION_1;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_TRANSITION_2;

import at.hsol.fountainizer.parser.content.ParserContent;
import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.interfaces.Line;
import at.hsol.fountainizer.parser.interfaces.MarginType;

/**
 * @author Felix Batusic
 */
public class TypeHelper {
    ParserContent outputLines;

    public TypeHelper(ParserContent outputLines) {
	this.outputLines = outputLines;
    }

    public LineMargins getType(SimpleLine l) {
	if (isHeading(l)) {
	    return LineMargins.HEADING;
	} else if (isParenthetical(l)) {
	    return LineMargins.PARENTHETICAL;
	} else if (isLyrics(l)) {
	    return LineMargins.LYRICS;
	} else if (isCentered(l)) {
	    return LineMargins.CENTERED;
	} else if (isPagebreak(l)) {
	    return LineMargins.PAGEBREAK;
	} else if (isTransition(l)) {
	    return LineMargins.TRANSITION;
	} else if (isCharacter(l)) {
	    return LineMargins.CHARACTER;
	} else if (isDialogue(l)) {
	    return LineMargins.DIALOGUE;
	} else {
	    return LineMargins.ACTION;
	}
    }

    private boolean isHeading(SimpleLine l) {
	String text = l.getText();
	if (l.getPrev() != null) {
	    if (text != null && l.getPrev().emptyText()) {
		return text.matches(L_HEADING);
	    }
	}
	return false;
    }

    private boolean isCharacter(SimpleLine l) {
	String text = l.getText();
	if (outputLines.getCharacters().lookup(text) != null) {
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
		if (isUpper && (l.getPrev().emptyText() && !l.getNext().emptyText())) {
		    return true;
		} else {
		    return false;
		}
	    }
	    return false;
	}
    }

    private boolean isDialogue(SimpleLine l) {
	Line prevLine = l.getPrev();
	if (prevLine != null) {
	    MarginType prevType = prevLine.getLineType();
	    if (l.getText() == null) {
		if (prevType == LineMargins.DIALOGUE) {
		    return true;
		}
	    } else if (prevType == LineMargins.CHARACTER || prevType == LineMargins.PARENTHETICAL) {
		return true;
	    }
	}
	return false;
    }

    private boolean isParenthetical(SimpleLine l) {
	String text = l.getText();
	if (text != null && text.matches(L_PARENTHETICAL)) {
	    return true;
	}
	return false;

    }

    private boolean isTransition(SimpleLine l) {
	String text = l.getText();
	if (l.getPrev() != null && l.getNext() != null) {
	    if (l.getPrev().emptyText() && l.getNext().emptyText()) {
		if (text.matches(L_TRANSITION_2) || text.matches(L_TRANSITION_1)) {
		    return true;
		}
	    }
	}
	return false;
    }

    private boolean isLyrics(SimpleLine l) {
	String text = l.getText();
	if (text != null) {
	    return text.startsWith(L_LYRICS);
	} else {
	    return false;
	}
    }

    private boolean isCentered(SimpleLine l) {
	String text = l.getText();
	if (text != null && text.matches(L_CENTERED)) {
	    return true;
	} else {
	    return false;
	}
    }

    private boolean isPagebreak(SimpleLine l) {
	String text = l.getText();
	if (text != null) {
	    return text.matches(L_PAGEBREAK);
	}
	return false;
    }

}
