package at.hacksolutions.f2p.parser.line;
import java.util.LinkedList;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserType;
import at.hacksolutions.f2p.pdfbox.*;

public class SimpleLine implements ParserLine{ 
    /**
     * Contains the original line of text in the file. May be null if line is
     * empty.
     */
    private String text;

    /**
     * The line number, starts at 0
     */
    private int lineNr;

    /**
     * Contains the Type of the line.
     */
    private ParserType type;

    /**
     * True if it is a (dialogue type) dual dialogue
     */
    private boolean dualDialogue;

    /**
     * Contains the line number if lineType is character
     */
    private int takeNumber;

    public SimpleLine(String text, int lineNr) {
	this.text = text;
	this.lineNr = lineNr;
	this.dualDialogue = false;
    }

    public ParserType getLineType() {
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
    
    public void incLineNr() {
	this.lineNr++;
    }
    
    public void decLineNr() {
	this.lineNr--;
    }
    
    public boolean emptyText() {
	return this.text == null;
    }

    public LinkedList<Paragraph> getParagraphForPDF() {
	if (getText() != null) {
	    RichString s = new RichString(getText(), null);
	    Paragraph p = new Paragraph(s);
	    p.setUppercase(type.isUppercase());
	    p.setUnderlined(type.isUnderlined());
	    p.setCentered(type.isCentered());
	    p.setMargin(type.getMarginTop(), type.getMarginLeft(), type.getMarginRight(),
		    type.getMarginBottom());
	    LinkedList<Paragraph> paragraphs = new LinkedList<>();
	    paragraphs.add(p);
	    return paragraphs;
	} else {
	    return null;
	}
    }

}
