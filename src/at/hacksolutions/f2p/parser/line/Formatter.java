package at.hacksolutions.f2p.parser.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.hacksolutions.f2p.parser.interfaces.ParserType;
import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;
import at.hacksolutions.f2p.parser.types.TitleLineType;

/**
 * @author Felix Batusic
 */
public class Formatter {
    public static String format(String s, ParserType type) {
	if (s != null) {
	    s = s.trim();
	}

	if (type == LineType.HEADING) {
	    Pattern p = Pattern.compile("\\.(.*?)");
	    Matcher m = p.matcher(s);
	    if (m.find()) {
		String rets = s.substring(m.end(), s.length()).trim();
		if (rets.isEmpty()) {
		    return s;
		} else {
		    return rets.trim();
		}
	    } else {
		return s;
	    }
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
		    return rets.trim();
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
	} else if (type == TitleLineType.CENTERED) {
	    Pattern p = Pattern.compile(ParserConstants.TP_CENTERED);
	    Matcher m = p.matcher(s);
	    if (m.find()) {
		String rets = s.substring(m.end(), s.length());
		if (rets.isEmpty()) {
		    return null;
		} else {
		    return rets.trim();
		}
	    } else {
		return null;
	    }

	} else if (type == TitleLineType.LEFT) {
	    Pattern p = Pattern.compile(ParserConstants.TP_LEFT);
	    Matcher m = p.matcher(s);
	    if (m.find()) {
		String rets = s.substring(m.end(), s.length());
		if (rets.isEmpty()) {
		    return null;
		} else {
		    return rets.trim();
		}
	    } else {
		return s.trim();
	    }
	} else {
	    return null;
	}
    }
}
