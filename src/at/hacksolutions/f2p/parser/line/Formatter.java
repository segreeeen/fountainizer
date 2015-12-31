package at.hacksolutions.f2p.parser.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;
import at.hacksolutions.f2p.parser.types.ParserType;

class Formatter {
    public static String format(String s, ParserType type) {
	if (type == LineType.HEADING) {
	    return s;
	} else if (type == LineType.CHARACTER) {
	    return s;
	} else if (type == LineType.DIALOGUE) {
	    return s;
	} else if (type == LineType.PARENTHETICAL) {
	    return s; 
	} else if (type == LineType.TRANSITION) {
	    return s;
	} else if (type == LineType.ACTION) {
	    return s;
	} else if (type == LineType.LYRICS) {
	    return s;
	} else if (type == LineType.CENTERED) {
	    final Pattern pattern = Pattern.compile(ParserConstants.L_CENTERED);
	    final Matcher matcher = pattern.matcher(s);
	    matcher.find();
	    return matcher.group(1);
	} else if (type == LineType.PAGEBREAK) {
	    return s;
	} else {
	    return s;
	}
    }
    
}
