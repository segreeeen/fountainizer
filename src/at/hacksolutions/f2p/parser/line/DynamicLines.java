package at.hacksolutions.f2p.parser.line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class DynamicLines implements ParserLines {
    ArrayList<SimpleLine> lines;

    public DynamicLines() {
	lines = new ArrayList<>(100);
    }

    DynamicLines(SimpleLine[] array) {
	lines = new ArrayList<SimpleLine>(Arrays.asList(array));
    }

    @Override
    public Iterator<SimpleLine> iterator() {
	return lines.iterator();
    }

    @Override
    public SimpleLine get(int index) {
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
	    SimpleLine lastLine = lines.get(lines.size()-1);
	    int lineNr = lastLine.getLineNr()+1;
	    SimpleLine l;
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
	if (index > 0 && index < lines.size() - 1) {
	    SimpleLine nextLine = lines.get(index);
	    incFollowing(nextLine);
	    lines.add(index, l);
	} else if (index == lines.size() - 1) {
	    add(text);
	}
    }

    public void remove(int index) {
	if (index > 0 && index < lines.size()) {
	    SimpleLine l = lines.get(index);
	    decFollowing(l);
	    lines.remove(index);
	}
    }

    private void decFollowing(SimpleLine l) {
	int start = l.getLineNr();
	int end = getLineCount();
	for (int i = start; i < end; i++) {
	    lines.get(i).decLineNr();
	}
    }

    private void incFollowing(SimpleLine l) {
	int start = l.getLineNr();
	int end = getLineCount();
	for (int i = start; i < end; i++) {
	    lines.get(i).incLineNr();
	}
    }


}
