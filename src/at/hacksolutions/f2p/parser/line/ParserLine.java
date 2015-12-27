package at.hacksolutions.f2p.parser.line;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.pdfbox.*;


public abstract class ParserLine {
    protected final String text;
    protected final int lineNr;
    protected LineType type;

    public ParserLine(String text, int lineNr) {
	this.text = text;
	this.lineNr = lineNr;
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
