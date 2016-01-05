package at.hsol.fountainizer.parser.line;

import java.util.LinkedList;

import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserType;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

/**
 * This Line is used as a substitute for all the title Lines
 * and in the end is printed as a Title Page. It consists of a List of
 * TitleLines that are found in the first run of parsing. It does not
 * contain any actual text.
 * 
 * @author SeGreeeen 
 * 
 */
public class TitlePage implements ParserLine {
    private LinkedList<TitlePageLine> titlePageLines;
    private ParserType type = LineType.TITLE;

    public TitlePage() {
	this.titlePageLines = new LinkedList<>();
    }

    public void addLine(TitlePageLine l) {
	titlePageLines.add(l);
    }

    @Override
    public LinkedList<Paragraph> getParagraphForPDF() {
	LinkedList<Paragraph> p = new LinkedList<>();
	for (TitlePageLine t : titlePageLines) {
	    p.addAll(t.getParagraphForPDF());
	}
	return p;
    }

    @Override
    public ParserType getLineType() {
	return type;
    }

    @Override
    public boolean emptyText() {
	return titlePageLines.isEmpty();
    }

    @Override
    public String getText() {
	// TODO Auto-generated method stub
	return null;
    }



}
