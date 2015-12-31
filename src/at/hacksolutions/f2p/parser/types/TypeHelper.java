package at.hacksolutions.f2p.parser.types;

import static at.hacksolutions.f2p.parser.types.ParserConstants.L_CENTERED;
import static at.hacksolutions.f2p.parser.types.ParserConstants.L_CHARACTER;
import static at.hacksolutions.f2p.parser.types.ParserConstants.L_HEADING;
import static at.hacksolutions.f2p.parser.types.ParserConstants.L_LYRICS;
import static at.hacksolutions.f2p.parser.types.ParserConstants.L_PAGEBREAK;
import static at.hacksolutions.f2p.parser.types.ParserConstants.L_PARENTHETICAL;
import static at.hacksolutions.f2p.parser.types.ParserConstants.L_TRANSITION_1;
import static at.hacksolutions.f2p.parser.types.ParserConstants.L_TRANSITION_2;

import at.hacksolutions.f2p.parser.line.ParserLine;
import at.hacksolutions.f2p.parser.line.ParserLines;
import at.hacksolutions.f2p.parser.line.SimpleLine;

public class TypeHelper {

    public static LineType getType(SimpleLine l, ParserLines outputLines) {
	if (isHeading(l, outputLines)) {
	    return LineType.HEADING;
	} else if (isParenthetical(l)) {
	    return LineType.PARENTHETICAL;
	} else if (isLyrics(l)) {
	    return LineType.LYRICS;
	} else if (isCentered(l)) {
	    return LineType.CENTERED;
	} else if (isPagebreak(l)) {
	    return LineType.PAGEBREAK;
	} else if (isTransition(l, outputLines)) {
	    return LineType.TRANSITION;
	} else if (isCharacter(l, outputLines)) {
	    return LineType.CHARACTER;
	} else if (isDialogue(l, outputLines)) {
	    return LineType.DIALOGUE;
	} else {
	    return LineType.ACTION;
	}
    }

    private static boolean isHeading(SimpleLine l, ParserLines outputLines) {
	String text = l.getText();
	if (text != null && outputLines.pEmptyText(l)) {
	    return text.matches(L_HEADING);
	}
	return false;
    }

    private static boolean isCharacter(SimpleLine l, ParserLines outputLines) {
	String text = l.getText();

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

	    if (isUpper && (outputLines.pEmptyText(l)
		    && !outputLines.nEmptyText(l))) {
		return true;
	    } else {
		return false;
	    }
	}
    }

    private static boolean isDialogue(SimpleLine l, ParserLines outputLines) {
	ParserLine prevLine = outputLines.get(l.getLineNr() - 1);
	if (prevLine != null) {
	    ParserType prevType = prevLine.getLineType();
	    if (l.getText() == null) {
		if (prevType == LineType.DIALOGUE) {
		    return true;
		}
	    } else if (prevType == LineType.CHARACTER
		    || prevType == LineType.PARENTHETICAL) {
		return true;
	    }
	}
	return false;
    }

    private static boolean isParenthetical(SimpleLine l) {
	String text = l.getText();
	if (text != null && text.matches(L_PARENTHETICAL)) {
	    return true;
	}
	return false;

    }

    private static boolean isTransition(SimpleLine l, ParserLines outputLines) {
	String text = l.getText();
	if (outputLines.pEmptyText(l) && outputLines.nEmptyText(l)) {
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

    private static boolean isLyrics(SimpleLine l) {
	String text = l.getText();
	if (text != null) {
	    return text.startsWith(L_LYRICS);
	} else {
	    return false;
	}
    }

    private static boolean isCentered(SimpleLine l) {
	String text = l.getText();
	if (text != null && text.matches(L_CENTERED)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isPagebreak(SimpleLine l) {
	String text = l.getText();
	if (text != null) {
	    return text.matches(L_PAGEBREAK);
	}
	return false;
    }
    
    
}
