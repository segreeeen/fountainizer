package at.hsol.fountainizer.parser.content;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.hsol.fountainizer.parser.interfaces.MarginType;
import at.hsol.fountainizer.parser.types.LineMargins;
import at.hsol.fountainizer.parser.types.ParserConstants;
import at.hsol.fountainizer.parser.types.TitlePageType;

/**
 * @author Felix Batusic
 */
public class Formatter {
    public static String format(String s, MarginType type) {
	if (s != null) {
	    s = s.trim();
	}

	if (type == LineMargins.HEADING) {
	    return formatColonTag(s, ParserConstants.L_HEADING);
	} else if (type == LineMargins.CHARACTER) {
	    if (s.contains("@")) {
		return s.replaceFirst("@", "");
	    }
	    return s;
	} else if (type == LineMargins.DIALOGUE) {
	    return s;
	} else if (type == LineMargins.PARENTHETICAL) {
	    return s;
	} else if (type == LineMargins.TRANSITION) {
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
	} else if (type == LineMargins.ACTION) {
	    return s;
	} else if (type == LineMargins.LYRICS) {
	    return s;
	} else if (type == LineMargins.CENTERED) {
	    final Pattern pattern = Pattern.compile(ParserConstants.L_CENTERED);
	    final Matcher matcher = pattern.matcher(s);
	    matcher.find();
	    return matcher.group(1).trim();
	} else if (type == LineMargins.PAGEBREAK) {
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
