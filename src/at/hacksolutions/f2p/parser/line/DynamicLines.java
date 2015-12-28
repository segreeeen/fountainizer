package at.hacksolutions.f2p.parser.line;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class DynamicLines implements LinesList {
    LinkedList<Line> lines;

    public DynamicLines() {
	lines = new LinkedList<>();
    }

    DynamicLines(Line[] array) {
	lines = new LinkedList<Line>(Arrays.asList(array));
    }

    @Override
    public Iterator<Line> iterator() {
	return lines.iterator();
    }

    @Override
    public Line get(int index) {
	if (index >= 0 && index < lines.size()) {
	    return lines.get(index);
	} else {
	    return null;
	}
    }

    @Override
    public Line getNext(Line l) {
	int index = l.getLineNr() + 1;
	if (index > 0 && index < lines.size()) {
	    return lines.get(index);
	} else {
	    return null;
	}
    }

    @Override
    public Line getPrev(Line l) {
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
    public boolean pEmptyText(Line l) {
	if (getPrev(l) != null) {
	    return getPrev(l).emptyText();
	} else {
	    return true;
	}
    }

    @Override
    public boolean nEmptyText(Line l) {
	if (getNext(l) != null) {
	    return getNext(l).emptyText();
	} else {
	    return true;
	}
    }

    public void add(String text) {
	if (!lines.isEmpty()) {
	    Line lastLine = lines.getLast();
	    int lineNr = lastLine.getLineNr()+1;
	    Line l;
	    if (text == null || text.isEmpty()) {
		l = new Line(null, lineNr);
	    } else {
		l = new Line(text, lineNr++);
	    }
	    lines.addLast(l);
	} else {
	    Line l;
	    if (text == null || text.isEmpty()) {
		l = new Line(null, 0);
	    } else {
		l = new Line(text, 0);
	    }
	    lines.addLast(l);
	}
    }

    public void add(String text, int index) {
	Line l;
	if (text == null || text.isEmpty()) {
	    l = new Line(null, index);
	} else {
	    l = new Line(text, index);
	}
	if (index > 0 && index < lines.size() - 1) {
	    Line nextLine = lines.get(index);
	    incFollowing(nextLine);
	    lines.add(index, l);
	} else if (index == lines.size() - 1) {
	    add(text);
	}
    }

    public void remove(int index) {
	if (index > 0 && index < lines.size()) {
	    Line l = lines.get(index);
	    decFollowing(l);
	    lines.remove(index);
	}
    }

    private void decFollowing(Line l) {
	int start = l.getLineNr();
	int end = getLineCount();
	for (int i = start; i < end; i++) {
	    lines.get(i).decLineNr();
	}
    }

    private void incFollowing(Line l) {
	int start = l.getLineNr();
	int end = getLineCount();
	for (int i = start; i < end; i++) {
	    lines.get(i).incLineNr();
	}
    }

}
