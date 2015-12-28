package at.hacksolutions.f2p.parser.types;

import static at.hacksolutions.f2p.parser.types.ParserConstants.*;

import at.hacksolutions.f2p.parser.line.GeneralLine;
import at.hacksolutions.f2p.parser.line.Lines;
import at.hacksolutions.f2p.parser.line.ParserLine;

public enum LineType {
    HEADING(true, false, false, 40.0F, 0F, 15.0F, 15.0F), 
    CHARACTER(true, false, false, 250.0F, 0F, 15F, 0.0F), 
    DIALOGUE(false, false, false, 150.0F, 70F, 0.2F, 0.0F), 
    PARENTHETICAL(false, false, false, 200.0F, 0F, 0.2F, 0.0F), 
    TRANSITION(false, false, false, 10.0F, 0F, 10.0F, 10.0F),  
    ACTION(false, false, false, 40.0F, 0F, 5.0F, 0.2F), 
    LYRICS(false, false, false, 10.0F, 0F, 10.0F, 10.0F), 
    CENTERED(false, true, false, 10.0F, 0F, 10.0F, 10.0F), 
    PAGEBREAK(false, false, false, 0F, 0F, 0F, 0F),  
    EMPTY(false, false, false, 0F, 0F, 0F, 0F), ;

    private final boolean uppercase;
    private final boolean centered;
    private final boolean underlined;
    private final float marginLeft;
    private final float marginRight;
    private final float marginTop;
    private final float marginBottom;
    
    private LineType(boolean uppercase, boolean centered, boolean underlined, 
	    float marginLeft, float marginRight, float marginTop, float marginBottom) {
	this.uppercase = uppercase;
	this.centered = centered;
	this.underlined = underlined;
	this.marginLeft = marginLeft;
	this.marginRight = marginRight;
	this.marginTop = marginTop;
	this.marginBottom = marginBottom;
    }
    
    public boolean isUppercase() {
        return uppercase;
    }

    public boolean isCentered() {
        return centered;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public float getMarginLeft() {
        return marginLeft;
    }

    public float getMarginTop() {
        return marginTop;
    }

    public float getMarginBottom() {
        return marginBottom;
    }



    public static LineType getType(ParserLine l, Lines outputLines) {
	if (isHeading(l, outputLines)) {
	    return LineType.HEADING;
	} else if (isCharacter(l, outputLines)) {
	    return LineType.CHARACTER;
	} else if (isParenthetical(l)) {
	    return LineType.PARENTHETICAL;
<<<<<<< HEAD
	}  else if (isLyrics(l)) {
=======
	} else if (isCentered(l)) {
	    return LineType.CENTERED;
	} else if (isTransition(l, outputLines)) {
	    return LineType.TRANSITION;
	} else if (isLyrics(l)) {
>>>>>>> 102154bfdccec8b44020564566e846176c7539f2
	    return LineType.LYRICS;
	}  else if (isPagebreak(l)) {
	    return LineType.PAGEBREAK;
	}else if (isTransition(l, outputLines)) {
	    return LineType.TRANSITION;
	} else if (isDialogue(l, outputLines)) {
	    return LineType.DIALOGUE;
	} else {
	    return LineType.ACTION;
	}
    }

    private static boolean isHeading(ParserLine l, Lines outputLines) {
	String text = l.getText();
	if (text != null && outputLines.pEmptyText(l)) {
	    return text.matches(L_HEADING);
	}
	return false;
    }

    private static boolean isCharacter(ParserLine l, Lines outputLines) {
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

    private static boolean isDialogue(ParserLine l, Lines outputLines) {
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

    private static boolean isParenthetical(ParserLine l) {
	String text = l.getText();
	if (text != null && text.matches(L_PARENTHETICAL)) {
	    return true;
	}
	return false;

    }

    private static boolean isTransition(ParserLine l, Lines outputLines) {
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

    private static boolean isLyrics(ParserLine l) {
	String text = l.getText();
	if (text != null) {
	    return text.startsWith(L_LYRICS);
	} else {
	    return false;
	}
    }

    private static boolean isCentered(ParserLine l) {
	String text = l.getText();
	if (text != null && text.matches(L_CENTERED)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isPagebreak(ParserLine l) {
	String text = l.getText();
	if (text != null) {
	    return text.matches(L_PAGEBREAK);
	}
	return false;
    }

    public float getMarginRight() {
	return marginRight;
    }

}
