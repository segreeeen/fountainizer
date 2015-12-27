package at.hacksolutions.f2p;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;

public class blaaaa {
    public static void main(String[] args) {
	Pattern pattern = Pattern.compile(ParserConstants.LT_ITALIC_END);
	Matcher matcher = pattern.matcher("Das ist ein* test *italic text*    cxvbc");
	// Check all occurrences
	while (matcher.find()) {
	    System.out.println(" End index: " + matcher.end());
	}
	
	LineType t = LineType.CHARACTER;
	System.out.println(t.isUppercase());
    }
}
