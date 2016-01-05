package at.hsol.fountainizer.parser.line;

import java.util.LinkedList;

import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

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
    public boolean emptyText() {
	return false;
    }

    @Override
    public String getText() {
	return null;
    }

}
