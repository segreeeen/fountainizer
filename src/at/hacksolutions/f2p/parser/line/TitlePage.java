package at.hacksolutions.f2p.parser.line;

import java.util.LinkedList;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserType;
import at.hacksolutions.f2p.pdfbox.Paragraph;

/**
 * @author SeGreeeen
 * This Line is used as a substitute for all the title Lines and in the end is
 * printed as a Title Page. It consists of a List of TitleLines that are 
 * found in the first run of parsing. It does not contain any actual text.
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
	for (TitlePageLine t: titlePageLines) {
	    p.addAll(t.getParagraphForPDF()); 
	}
	return p;
    }


    @Override
    public ParserType getLineType() {
	return type;
    }


    @Override
    public int getLineNr() {
	// TODO Auto-generated method stub
	return 0;
    }


    @Override
    public void setDualDialogue(boolean b) {
	// TODO Auto-generated method stub
	
    }


    @Override
    public boolean emptyText() {
	return false;
    }


    @Override
    public String getText() {
	// TODO Auto-generated method stub
	return null;
    }


    @Override
    public void setLineType(ParserType type) {
	// TODO Auto-generated method stub
	
    }

}
