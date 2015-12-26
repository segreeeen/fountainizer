package at.hacksolutions.f2p.parser;

public class Parser {
    
    public Lines parse(Lines outputLines) {
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    Line l = outputLines.get(i);
	    setAttributes(l, outputLines);
	}
	return outputLines;
    }

    private void setAttributes(Line l, Lines outputLines) {
	String text = l.getText();
	LineType type = LineType.getType(l, outputLines);
	l.setLineType(type);
	
	if (type == LineType.DIALOGUE) {
	    if (text.matches("(.*?)^")) {
		if (outputLines.get(l.getLineNr() - 1) != null) {
		    l.setDualDialogue(true);
		    outputLines.get(l.getLineNr() - 1).setDualDialogue(true);
		}
	    }
	}
    }

}
