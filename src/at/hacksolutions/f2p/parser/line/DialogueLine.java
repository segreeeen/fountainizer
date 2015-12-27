package at.hacksolutions.f2p.parser.line;

import at.hacksolutions.f2p.parser.types.LineType;

public class DialogueLine extends ParserLine {
    private boolean dualDialogue;

    public DialogueLine(String text, int lineNr) {
	super(text, lineNr);
	this.dualDialogue = false;
    }

    public LineType getLineType() {
	return type;
    }

    public void setLineType(LineType lineType) {
	this.type = lineType;
    }

    public boolean isDualDialogue() {
	return dualDialogue;
    }

    public void setDualDialogue(boolean dualDialogue) {
	this.dualDialogue = dualDialogue;
    }

    public String getText() {
	return text;
    }

    public int getLineNr() {
	return lineNr;
    }

}
