package at.hacksolutions.f2p.parser.line;

import java.util.Iterator;

public class FixedLines implements ParserLines{
    private SimpleLine[] lines;
    private int lineCount;

    public FixedLines(SimpleLine[] lines) {
	this.lines = lines;
	this.lineCount = lines.length;
    }

    public SimpleLine get(int index) { 
	return (index >= 0 && index < lineCount) ? lines[index] : null;
    }

    public SimpleLine getNext(SimpleLine l) {
	return (l.getLineNr() + 1 >= 0 && l.getLineNr() + 1 < lineCount)
		? lines[l.getLineNr() + 1] : null;
    }

    public SimpleLine getPrev(SimpleLine l) {
	return (l.getLineNr() - 1 >= 0 && l.getLineNr() - 1 < lineCount)
		? lines[l.getLineNr() - 1] : null;
    }

    public int getLineCount() {
	return lineCount;
    }

    public boolean pEmptyText(SimpleLine l) {
	if (l != null && getPrev(l) != null) {
	    return getPrev(l).getText() == null;
	}
	return true;
    }

    public boolean nEmptyText(SimpleLine l) {
	if (l != null && getNext(l) != null) {
	    return getNext(l).getText() == null;
	}
	return true;
    }

    @Override
    public Iterator<SimpleLine> iterator() {
	return new Iterator<SimpleLine>() {
	    int i = 0;

	    @Override
	    public boolean hasNext() {
		return i < lineCount;
	    }

	    @Override
	    public SimpleLine next() {
		SimpleLine l = lines[i];
		i++;
		return l;
	    }
	    
	};
    }
    
    public DynamicLines asDynamicLines() {
	return new DynamicLines(lines);
    }

    @Override
    public ParserLine getNext(ParserLine l) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ParserLine getPrev(ParserLine iterator) {
	// TODO Auto-generated method stub
	return null;
    }
}
