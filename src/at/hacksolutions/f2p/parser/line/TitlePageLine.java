package at.hacksolutions.f2p.parser.line;

import java.util.LinkedList;

import at.hacksolutions.f2p.parser.interfaces.ParserLine;
import at.hacksolutions.f2p.parser.interfaces.ParserType;
import at.hacksolutions.f2p.pdfbox.paragraph.Paragraph;

/**
 * @author Felix Batusic
 */
public class TitlePageLine implements ParserLine {
    private ParserType type;
    private LinkedList<ParserLine> lines;

    public LinkedList<ParserLine> getLines() {
	return lines;
    }

    public TitlePageLine(ParserType type) {
	this.lines = new LinkedList<>();
	this.type = type;
    }

    @Override
    public LinkedList<Paragraph> getParagraphForPDF() {
	LinkedList<Paragraph> p = new LinkedList<>();
	for (ParserLine t : lines) {
	    p.addAll(t.getParagraphForPDF());
	}
	return p;
    }

    public void addLine(ParserLine iterator) {
	lines.add(iterator);
    }

    @Override
    public ParserType getLineType() {
	return type;
    }

    @Override
    public int getLineNr() {
	return 0;
    }

    @Override
    public void setDualDialogue(boolean b) {

    }

    @Override
    public boolean emptyText() {
	return false;
    }

    @Override
    public String getText() {
	return null;
    }

    @Override
    public void setLineType(ParserType type) {

    }

    @Override
    public void decLineNr() {

    }

    @Override
    public void incLineNr() {

    }
}
