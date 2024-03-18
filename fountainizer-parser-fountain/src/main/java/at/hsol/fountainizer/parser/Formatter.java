package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.types.LineType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Felix Batusic
 */
public class Formatter {
    static String format(String s, LineType type) {
        if (s != null) {
            s = s.trim();
            return formatLineType(s, type);
        }

        return null;
    }

    private static String formatLineType(String s, LineType type) {
        if (type == null) return s;

        switch (type) {
            case CHARACTER:
                if (s.contains("@")) {
                    return s.replaceFirst("@", "");
                }
                return s;
            case TRANSITION:
                Pattern p = Pattern.compile(ParserConstants.L_TRANSITION_2);
                Matcher m = p.matcher(s);
                if (m.find()) {
                    String rets = s.substring(m.end()).trim();
                    if (rets.isEmpty()) {
                        return s;
                    } else {
                        return rets.trim().toUpperCase();
                    }
                } else {
                    return s;
                }
            case CENTERED:
                final Pattern pattern = Pattern.compile(ParserConstants.L_CENTERED);
                final Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    return matcher.group(1).trim();
                }
                return s;
            case TITLEPAGE_CENTERED:
                return formatTitleText(s, ParserConstants.TP_CENTERED);
            case TITLEPAGE_LEFT:
                return formatTitleText(s, ParserConstants.TP_LEFT);
            case TITLEPAGE_TITLE:
                return formatTitleText(s, ParserConstants.TP_TITLE);
            default:
                return s;
        }
    }

    private static String formatTitleText(String s, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        if (m.find()) {
            String rets = s.substring(m.end());
            if (rets.isEmpty()) {
                return s;
            } else {
                return rets.trim();
            }
        } else {
            return s;
        }
    }
}
