package at.hacksolutions.f2p.parser.line;

import java.util.LinkedList;

import at.hacksolutions.f2p.parser.types.ParserType;
import at.hacksolutions.f2p.pdfbox.Paragraph;


public class TitlePageLine implements ParserLine {
    private ParserType type;
    private LinkedList<SimpleLine> lines;
    
    public TitlePageLine(ParserType type) {
	this.lines = new LinkedList<>();
	this.type = type;
    }
    
    
    @Override
    public LinkedList<Paragraph> getParagraphForPDF() {
	LinkedList<Paragraph> p = new LinkedList<>();
	for (SimpleLine t: lines) {
	    p.addAll(t.getParagraphForPDF()); 
	}
	return p;
    }
    
    public void addLine(SimpleLine l) {
	lines.add(l);
    }

    @Override
    public ParserType getLineType() {
	return type;
    }
}
