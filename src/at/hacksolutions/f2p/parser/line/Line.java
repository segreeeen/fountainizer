package at.hacksolutions.f2p.parser.line;
import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.pdfbox.*;

public class Line { 
    /**
     * Contains the original line of text in the file. May be null if line is
     * empty.
     */
    private String text;

    /**
     * The absolute line number, starts at 0(!)
     */
    private final int lineNr;

    /**
     * Contains the Type of the line.
     */
    private LineType type;

    /**
     * True if it is a (dialogue type) dual dialogue
     */
    private boolean dualDialogue;

    /**
     * Contains the line number if lineType is character
     */
    private int takeNumber;

    public Line(String text, int lineNr) {
	this.text = text;
	this.lineNr = lineNr;
	this.dualDialogue = false;
    }

    public LineType getLineType() {
	return type;
    }

    public void setLineType(LineType lineType) {
	this.type = lineType;
	setText(Formatter.format(getText(),lineType));
    }

    public boolean isDualDialogue() {
	return dualDialogue;
    }

    public void setDualDialogue(boolean dualDialogue) {
	this.dualDialogue = dualDialogue;
    }

    public int getTakeNumber() {
	return takeNumber;
    }

    public void setTakeNumber(int takeNumber) {
	this.takeNumber = takeNumber;
    }

    public String getText() {
	return text;
    }
    
    public void setText(String text) {
	this.text = text;
    }

    public int getLineNr() {
	return lineNr;
    }

    public Paragraph getParagraphForPDF() {
	if (getText() != null) {
	    RichString s = new RichString(getText(), null);
	    Paragraph p = new Paragraph(s);
	    p.setUppercase(type.isUppercase());
	    p.setUnderlined(type.isUnderlined());
	    p.setCentered(type.isCentered());
	    p.setMargin(type.getMarginTop(), type.getMarginLeft(), type.getMarginRight(),
		    type.getMarginBottom());
	    return p;
	} else {
	    return null;
	}
    }

}
