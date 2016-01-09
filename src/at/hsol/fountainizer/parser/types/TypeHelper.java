package at.hsol.fountainizer.parser.types;

import static at.hsol.fountainizer.parser.types.ParserConstants.L_CENTERED;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_CHARACTER;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_HEADING;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_LYRICS;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_PAGEBREAK;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_PARENTHETICAL;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_TRANSITION_1;
import static at.hsol.fountainizer.parser.types.ParserConstants.L_TRANSITION_2;

import at.hsol.fountainizer.parser.data.Statistic;
import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserType;
import at.hsol.fountainizer.parser.line.DynamicLines;
import at.hsol.fountainizer.parser.line.SimpleLine;

/**
 * @author Felix Batusic
 */
public class TypeHelper {
    Statistic stats;
    DynamicLines outputLines;

    public TypeHelper(Statistic stats, DynamicLines outputLines) {
	this.stats = stats;
	this.outputLines = outputLines;
    }

    public TypeHelper(DynamicLines outputLines) {
	this(null, outputLines);

    }

    public LineType getType(SimpleLine l) {
	if (isHeading(l)) {
	    if (stats != null) {
		stats.incHeading();
		l.setLineTypeNumber(stats.getHeading());
	    }
	    return LineType.HEADING;
	} else if (isParenthetical(l)) {
	    if (stats != null) {
		stats.incParenthetical();
		l.setLineTypeNumber(stats.getParenthetical());
	    }
	    return LineType.PARENTHETICAL;
	} else if (isLyrics(l)) {
	    if (stats != null) {
		stats.incLyrics();
		l.setLineTypeNumber(stats.getLyrics());
	    }
	    return LineType.LYRICS;
	} else if (isCentered(l)) {
	    if (stats != null) {
		stats.incCentered();
		l.setLineTypeNumber(stats.getCentered());
	    }
	    return LineType.CENTERED;
	} else if (isPagebreak(l)) {
	    return LineType.PAGEBREAK;
	} else if (isTransition(l)) {
	    if (stats != null) {
		stats.incTransition();
		l.setLineTypeNumber(stats.getTransition());
	    }
	    return LineType.TRANSITION;
	} else if (isCharacter(l)) {
	    if (stats != null) {
		stats.incCharacter();
		l.setLineTypeNumber(stats.getCharacter());
	    }
	    return LineType.CHARACTER;
	} else if (isDialogue(l)) {
	    if (stats != null) {
		stats.incDialogue();
		l.setLineTypeNumber(stats.getDialogue());
	    }
	    return LineType.DIALOGUE;
	} else {
	    if (stats != null) {
		stats.incAction();
		l.setLineTypeNumber(stats.getAction());
	    }
	    return LineType.ACTION;
	}
    }

    private boolean isHeading(SimpleLine l) {
	String text = l.getText();
	if (text != null && l.getPrev().emptyText()) {
	    return text.matches(L_HEADING);
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

	    if (isUpper && (l.getPrev().emptyText() && !l.getNext().emptyText())) {
		return true;
	    } else {
		return false;
	    }
	}
    }

    private boolean isDialogue(SimpleLine l) {
	ParserLine prevLine = l.getPrev();
	if (prevLine != null) {
	    ParserType prevType = prevLine.getLineType();
	    if (l.getText() == null) {
		if (prevType == LineType.DIALOGUE) {
		    return true;
		}
	    } else if (prevType == LineType.CHARACTER || prevType == LineType.PARENTHETICAL) {
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
	if (l.getPrev().emptyText() && l.getNext().emptyText()) {
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
