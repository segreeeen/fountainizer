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
			return s;
		} else if (type.getClass() == LineType.class) {
			return formatLineType(s, (LineType) type);
		} else if (type.getClass() == TitlePageType.class) {
			return formatTitleType(s, (TitlePageType) type);
		} else
			return null;

	}

	private static String formatTitleType(String s, TitlePageType type) {
		switch (type) {
		case CENTERED:
			return formatColonTag(s, ParserConstants.TP_CENTERED);
		case LEFT:
			return formatColonTag(s, ParserConstants.TP_LEFT);
		case TITLE:
			return formatColonTag(s, ParserConstants.TP_TITLE);
		default:
			return null;
		}
	}

	private static String formatLineType(String s, LineType type) {
		switch (type) {
		case CHARACTER:
			if (s.contains("@")) {
				return s.replaceFirst("@", "");
			}
			return s;
		case DIALOGUE:
			return s;
		case PARENTHETICAL:
			return s;
		case TRANSITION:
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
		case ACTION:
			return s;
		case LYRICS:
			return s;
		case CENTERED:
			final Pattern pattern = Pattern.compile(ParserConstants.L_CENTERED);
			final Matcher matcher = pattern.matcher(s);
			matcher.find();
			return matcher.group(1).trim();
		case PAGEBREAK:
			return s;
		default:
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
