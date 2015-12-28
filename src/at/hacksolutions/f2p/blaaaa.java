package at.hacksolutions.f2p;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;

public class blaaaa {
    public static void main(String[] args) {
	String bla = "> THE END <";
	System.out.println(bla.matches(ParserConstants.L_CENTERED));
	final Pattern pattern = Pattern.compile(ParserConstants.L_CENTERED);
	final Matcher matcher = pattern
		.matcher(">String I want to extract<");
	if (matcher.find() == true) {
	    System.out.println(matcher.group(1)); // Prints String I want to
						  // extract
	}
    }
}
