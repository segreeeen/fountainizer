package at.hacksolutions.f2p.parser.line;

import at.hacksolutions.f2p.parser.types.LineType;

public class GeneralLine extends ParserLine {

    public GeneralLine(String text, int lineNr) {
	super(text,lineNr);
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
