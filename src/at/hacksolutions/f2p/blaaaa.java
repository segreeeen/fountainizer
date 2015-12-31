package at.hacksolutions.f2p;

import java.io.IOException;
import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.line.DynamicLines;
import at.hacksolutions.f2p.parser.line.SimpleLine;
import at.hacksolutions.f2p.parser.types.ParserConstants;
import at.hacksolutions.f2p.parser.types.TypeHelper;

public class blaaaa {
    public static void main(String[] args) throws IOException {
	String text = ">centered fuck tag<";
	System.out.println(text.matches(ParserConstants.L_CENTERED));
    }
}
