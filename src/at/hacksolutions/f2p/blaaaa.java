package at.hacksolutions.f2p;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;
import at.hacksolutions.f2p.parser.types.ParserType;
import at.hacksolutions.f2p.parser.types.TitleLineType;

public class blaaaa {
    public static void main(String[] args) {
	ParserType t = TitleLineType.CENTERED;
	if (t.getClass() == TitleLineType.class) {
	    System.out.println("lal");
	}
    }
}
