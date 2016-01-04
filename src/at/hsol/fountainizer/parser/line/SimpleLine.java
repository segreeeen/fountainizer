package at.hsol.fountainizer.parser.line;

import java.util.LinkedList;

import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

/**
 * @author Felix Batusic
 */
public class SimpleLine implements ParserLine {
    private String text;
    private int lineNr;
    private ParserType type;
    private boolean dualDialogue;
    private int takeNumber;

    public SimpleLine(String text, int lineNr) {
	this.text = text;
	this.lineNr = lineNr;
	this.dualDialogue = false;
    }

    public ParserType getLineType() {
	return type;
    }

    public void setLineType(ParserType type) {
	this.type = type;
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
	    p.setLinetype(type);
	    p.setUppercase(type.isUppercase());
	    p.setUnderlined(type.isUnderlined());
	    p.setCentered(type.isCentered());
	    p.setMargin(type.getMarginTop(), type.getMarginLeft(), type.getMarginRight(), type.getMarginBottom());
	    LinkedList<Paragraph> paragraphs = new LinkedList<>();
	    paragraphs.add(p);
	    return paragraphs;
	} else {
	    LinkedList<Paragraph> paragraphs = new LinkedList<>();
	    paragraphs.add(Paragraph.getEmptyParagraph());
	    return paragraphs;
	}
    }

}
