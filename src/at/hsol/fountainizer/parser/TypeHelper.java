package at.hsol.fountainizer.parser;

import static at.hsol.fountainizer.parser.ParserConstants.*;

import at.hsol.fountainizer.parser.content.ParserContent;
import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.interfaces.Line;
import at.hsol.fountainizer.parser.interfaces.MarginType;
import at.hsol.fountainizer.parser.meta.Statistic;
import at.hsol.fountainizer.parser.types.HeadingType;
import at.hsol.fountainizer.parser.types.LineType;

/**
 * @author Felix Batusic
 */
public class TypeHelper {
    ParserContent outputLines;
    Statistic stats;

    public TypeHelper(ParserContent outputLines, Statistic stats) {
	this.outputLines = outputLines;
	this.stats = stats;
    }

    public MarginType getType(SimpleLine l) {
	if (isHeading(l)) {
	    return getHeading(l);
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

    private boolean isHeading(SimpleLine l) {
	String text = l.getText();
	if (l.getPrev() != null) {
	    if (text != null && l.getPrev().emptyText()) {
		return text.matches(L_HEADING);
	    }
	}
	return false;
    }

    private MarginType getHeading(SimpleLine l) {
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

    private boolean isCharacter(SimpleLine l) {
	String text = l.getText();
	if (stats.getCharacters().lookup(text) != null) {
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
	    if (prevType == LineType.DIALOGUE || prevType == LineType.CHARACTER || prevType == LineType.PARENTHETICAL) {
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
