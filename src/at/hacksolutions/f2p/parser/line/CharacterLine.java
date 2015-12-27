package at.hacksolutions.f2p.parser.line;

import at.hacksolutions.f2p.parser.types.LineType;

public class CharacterLine extends ParserLine {
    private int takeNr;

    public CharacterLine(String text, int lineNr, int takeNr) {
	super(text, lineNr);
    }

    public LineType getLineType() {
	return type;
    }

    public void setLineType(LineType lineType) {
	this.type = lineType;
    }

    public int getTakeNumber() {
	return takeNr;
    }

    public void setTakeNumber(int takeNr) {
	this.takeNr = takeNr;
    }

    public String getText() {
	return text;
    }

    public int getLineNr() {
	return lineNr;
    }
    

}
