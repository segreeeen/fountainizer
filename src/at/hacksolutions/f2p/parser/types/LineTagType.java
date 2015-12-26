package at.hacksolutions.f2p.parser.types;

import static at.hacksolutions.f2p.parser.types.ParserConstants.*;

import at.hacksolutions.f2p.parser.line.Line;
import at.hacksolutions.f2p.parser.line.LineTags;

public enum LineTagType {
    UNDERLINED, ITALIC, BOLD, COMMENTARY, NOTE, BITALICS;

    public static LineTags getLineTags(Line l) {
	String text = l.getText();
	LineTags tags = new LineTags();

	if (isUnderlinedStart(text)) {
	    tags.pushStart(LineTagType.UNDERLINED);
	}

	if (isUnderlinedEnd(text)) {
	    tags.pushEnd(LineTagType.UNDERLINED);
	}

	if (isItalicStart(text)) {
	    tags.pushStart(LineTagType.ITALIC);
	}

	if (isItalicEnd(text)) {
	    tags.pushEnd(LineTagType.ITALIC);
	}

	if (isBoldStart(text)) {
	    tags.pushStart(LineTagType.BOLD);
	}

	if (isBoldEnd(text)) {
	    tags.pushEnd(LineTagType.BOLD);
	}

	if (isCommentaryStart(text)) {
	    tags.pushStart(LineTagType.COMMENTARY);
	}

	if (isCommentaryEnd(text)) {
	    tags.pushEnd(LineTagType.COMMENTARY);
	}

	if (isNoteStart(text)) {
	    tags.pushStart(LineTagType.NOTE);
	}

	if (isNoteEnd(text)) {
	    tags.pushEnd(LineTagType.NOTE);
	}

	if (isBItalicsStart(text)) {
	    tags.pushStart(LineTagType.BITALICS);
	}

	if (isBItalicsEnd(text)) {
	    tags.pushEnd(LineTagType.BITALICS);
	}

	return tags;
    }

    private static boolean isUnderlinedStart(String text) {
	if (text.matches(LT_UNDERLINE_START)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isUnderlinedEnd(String text) {
	if (text.matches(LT_UNDERLINE_END)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isItalicStart(String text) {
	if (text.matches(LT_ITALIC_START)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isItalicEnd(String text) {
	if (text.matches(LT_ITALIC_END)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isBoldStart(String text) {
	if (text.matches(LT_BOLD_START)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isBoldEnd(String text) {
	if (text.matches(LT_BOLD_END)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isCommentaryStart(String text) {
	if (text.matches(LT_COMMENTARY_START)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isCommentaryEnd(String text) {
	if (text.matches(LT_COMMENTARY_END)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isNoteStart(String text) {
	if (text.matches(LT_NOTE_START)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isNoteEnd(String text) {
	if (text.matches(LT_NOTE_END)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isBItalicsStart(String text) {
	if (text.matches(LT_BITALICS_START)) {
	    return true;
	} else {
	    return false;
	}
    }

    private static boolean isBItalicsEnd(String text) {
	if (text.matches(LT_BITALICS_END)) {
	    return true;
	} else {
	    return false;
	}
    }

}
