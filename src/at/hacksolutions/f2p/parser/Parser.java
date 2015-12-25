package at.hacksolutions.f2p.parser;

public class Parser {
    private Lines outputLines;

    public Parser(Lines inputLines, boolean[] emptyLines) {
	this.outputLines = inputLines;
    }

    public Lines parse() {
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    Line l = outputLines.get(i);
	    setAttributes(l);
	}
	return outputLines;
    }

    private void setAttributes(Line l) {
	LineType type = LineType.getType(l, outputLines);
	l.setLineType(type);
	if ()
    }

   

}
