package at.hacksolutions.f2p.parser.line;

import java.util.Iterator;

public class FixedLines implements LinesList{
    private Line[] lines;
    private int lineCount;

    public FixedLines(Line[] lines) {
	this.lines = lines;
	this.lineCount = lines.length;
    }

    public Line get(int index) { 
	return (index >= 0 && index < lineCount) ? lines[index] : null;
    }

    public Line getNext(Line l) {
	return (l.getLineNr() + 1 >= 0 && l.getLineNr() + 1 < lineCount)
		? lines[l.getLineNr() + 1] : null;
    }

    public Line getPrev(Line l) {
	return (l.getLineNr() - 1 >= 0 && l.getLineNr() - 1 < lineCount)
		? lines[l.getLineNr() - 1] : null;
    }

    public int getLineCount() {
	return lineCount;
    }

    public boolean pEmptyText(Line l) {
	if (l != null && getPrev(l) != null) {
	    return getPrev(l).getText() == null;
	}
	return true;
    }

    public boolean nEmptyText(Line l) {
	if (l != null && getNext(l) != null) {
	    return getNext(l).getText() == null;
	}
	return true;
    }

    @Override
    public Iterator<Line> iterator() {
	return new Iterator<Line>() {
	    int i = 0;

	    @Override
	    public boolean hasNext() {
		return i < lineCount;
	    }

	    @Override
	    public Line next() {
		Line l = lines[i];
		i++;
		return l;
	    }
	    
	};
    }
    
    public DynamicLines asDynamicLines() {
	return new DynamicLines(lines);
    }
}
