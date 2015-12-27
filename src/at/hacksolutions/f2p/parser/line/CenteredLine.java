package at.hacksolutions.f2p.parser.line;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;

public class CenteredLine extends ParserLine {

    public CenteredLine(String text, int lineNr) {
	super(null,lineNr);
	final Pattern pattern = Pattern.compile(ParserConstants.L_CENTERED);
	final Matcher matcher = pattern.matcher(text);
	matcher.find();
	text = matcher.group(1);
	
    }

    public LineType getLineType() {
	return type;
    }

    public void setLineType(LineType lineType) {
	this.type = lineType;
    }

    public String getText() {
	return text;
    }

    public int getLineNr() {
	return lineNr;
    }

}

