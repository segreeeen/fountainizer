package at.hacksolutions.f2p.parser.line;

import java.util.Iterator;

public class Lines implements Iterable<ParserLine>{
    private ParserLine[] lines;
    private int lineCount;

    public Lines(ParserLine[] lines) {
	this.lines = lines;
	this.lineCount = lines.length;
    }

    public ParserLine get(int index) {
	return (index >= 0 && index < lineCount) ? lines[index] : null;
    }

    public ParserLine getNext(ParserLine l) {
	return (l.getLineNr() + 1 >= 0 && l.getLineNr() + 1 < lineCount)
		? lines[l.getLineNr() + 1] : null;
    }

    public ParserLine getPrev(ParserLine l) {
	return (l.getLineNr() - 1 >= 0 && l.getLineNr() - 1 < lineCount)
		? lines[l.getLineNr() - 1] : null;
    }

    public int getLineCount() {
	return lineCount;
    }

    public boolean pEmptyText(ParserLine l) {
	if (l != null && getPrev(l) != null) {
	    return getPrev(l).getText() == null;
	}
	return true;
    }

    public boolean nEmptyText(ParserLine l) {
	if (l != null && getNext(l) != null) {
	    return getNext(l).getText() == null;
	}
	return true;
    }

    @Override
    public Iterator<ParserLine> iterator() {
	return new Iterator<ParserLine>() {
	    int i = 0;

	    @Override
	    public boolean hasNext() {
		return i < lineCount;
	    }

	    @Override
	    public ParserLine next() {
		ParserLine l = lines[i];
		i++;
		return l;
	    }
	    
	};
    }
}
