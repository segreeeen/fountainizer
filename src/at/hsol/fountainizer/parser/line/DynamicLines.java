package at.hsol.fountainizer.parser.line;

import java.util.ArrayList;
import java.util.Iterator;

import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserLines;

/**
 * @author Felix Batusic
 */
public class DynamicLines implements ParserLines {
    private TitlePage tp;
    private ArrayList<ParserLine> lines;

    public DynamicLines() {
	lines = new ArrayList<>(100);
    }

    @Override
    public Iterator<ParserLine> iterator() {
	return lines.iterator();
    }

    @Override
    public ParserLine get(int index) {
	if (index >= 0 && index < lines.size()) {
	    return lines.get(index);
	} else {
	    return null;
	}
    }

    @Override
    public ParserLine getNext(ParserLine l) {
	int index = l.getLineNr() + 1;
	if (index > 0 && index < lines.size()) {
	    return lines.get(index);
	} else {
	    return null;
	}
    }

    @Override
    public ParserLine getPrev(ParserLine l) {
	int index = l.getLineNr() - 1;
	if (index > 0 && index < lines.size()) {
	    return lines.get(index);
	} else {
	    return null;
	}
    }

    @Override
    public int getLineCount() {
	return lines.size();
    }

    @Override
    public boolean pEmptyText(SimpleLine l) {
	if (getPrev(l) != null) {
	    return getPrev(l).emptyText();
	} else {
	    return true;
	}
    }

    @Override
    public boolean nEmptyText(SimpleLine l) {
	if (getNext(l) != null) {
	    return getNext(l).emptyText();
	} else {
	    return true;
	}
    }

    public void add(String text) {
	if (!lines.isEmpty()) {
	    ParserLine lastLine = lines.get(lines.size() - 1);
	    int lineNr = lastLine.getLineNr() + 1;
	    ParserLine l;
	    if (text == null || text.isEmpty()) {
		l = new SimpleLine(null, lineNr);
	    } else {
		l = new SimpleLine(text, lineNr++);
	    }
	    lines.add(l);
	} else {
	    SimpleLine l;
	    if (text == null || text.isEmpty()) {
		l = new SimpleLine(null, 0);
	    } else {
		l = new SimpleLine(text, 0);
	    }
	    lines.add(l);
	}
    }

    public void add(String text, int index) {
	SimpleLine l;
	if (text == null || text.isEmpty()) {
	    l = new SimpleLine(null, index);
	} else {
	    l = new SimpleLine(text, index);
	}
	if (index >= 0 && index < lines.size() - 1) {
	    ParserLine nextLine = lines.get(index);
	    incFollowing(nextLine);
	    lines.add(index, l);
	} else if (index == lines.size() - 1) {
	    add(text);
	}
    }

    public void remove(int index) {
	if (index >= 0 && index < lines.size()) {
	    ParserLine l = lines.get(index);
	    decFollowing(l);
	    lines.remove(index);
	}
    }

    private void decFollowing(ParserLine l) {
	int start = l.getLineNr();
	int end = getLineCount();
	for (int i = start; i < end; i++) {
	    lines.get(i).decLineNr();
	}
    }

    private void incFollowing(ParserLine nextLine) {
	int start = nextLine.getLineNr();
	int end = getLineCount();
	for (int i = start; i < end; i++) {
	    lines.get(i).incLineNr();
	}
    }

    @Override
    public boolean hasNext(ParserLine l) {
	return getNext(l) != null;
    }

    @Override
    public void remove(ParserLine l) {
	remove(l.getLineNr());
    }

    @Override
    public void add(TitlePage tp, int i) {
	lines.add(i, tp);

    }

    public TitlePage getTitlepage() {
	return tp;
    }

    public void setTitlepage(TitlePage tp) {
	this.tp = tp;
    }

}
