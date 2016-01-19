package at.hsol.fountainizer.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.hsol.fountainizer.parser.interfaces.MarginType;
import at.hsol.fountainizer.parser.types.HeadingType;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.parser.types.TitlePageType;

/**
 * @author Felix Batusic
 */
public class Formatter {
    public static String format(String s, MarginType type) {
	if (s != null) {
	    s = s.trim();
	}

	if (type.getClass() == HeadingType.class) {
	    return formatColonTag(s, ParserConstants.L_HEADING);
	} else if (type == LineType.CHARACTER) {
	    if (s.contains("@")) {
		return s.replaceFirst("@", "");
	    }
	    return s;
	} else if (type == LineType.DIALOGUE) {
	    return s;
	} else if (type == LineType.PARENTHETICAL) {
	    return s;
	} else if (type == LineType.TRANSITION) {
	    Pattern p = Pattern.compile(ParserConstants.L_TRANSITION_2);
	    Matcher m = p.matcher(s);
	    if (m.find()) {
		String rets = s.substring(m.end(), s.length()).trim();
		if (rets.isEmpty()) {
		    return s;
		} else {
		    return rets.trim().toUpperCase();
		}
	    } else {
		return s;
	    }
	} else if (type == LineType.ACTION) {
	    return s;
	} else if (type == LineType.LYRICS) {
	    return s;
	} else if (type == LineType.CENTERED) {
	    final Pattern pattern = Pattern.compile(ParserConstants.L_CENTERED);
	    final Matcher matcher = pattern.matcher(s);
	    matcher.find();
	    return matcher.group(1).trim();
	} else if (type == LineType.PAGEBREAK) {
	    return s;
	} else if (type == TitlePageType.CENTERED) {
	    return formatColonTag(s, ParserConstants.TP_CENTERED);
	} else if (type == TitlePageType.LEFT) {
	    return formatColonTag(s, ParserConstants.TP_LEFT);
	} else if (type == TitlePageType.RIGHT) {
	    return formatColonTag(s, ParserConstants.TP_RIGHT);
	} else if (type == TitlePageType.TITLE) {
	    return formatColonTag(s, ParserConstants.TP_TITLE);
	} else {
	    return null;
	}
    }

    private static String formatColonTag(String s, String regex) {
	Pattern p = Pattern.compile(regex);
	Matcher m = p.matcher(s);
	if (m.find()) {
	    String rets = s.substring(m.end(), s.length());
	    if (rets.isEmpty()) {
		return null;
	    } else {
		return rets.trim();
	    }
	} else {
	    return s;
	}
    }

}
