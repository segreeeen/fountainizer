package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.parser.Line;
import at.hsol.fountainizer.core.api.types.LineType;
import at.hsol.fountainizer.core.api.Options;

import java.util.*;

/**
 * @author Felix Batusic
 */
class ParserContent implements Content {
	private final ParserContentMetaData stats;
	private final ArrayList<Line> parserContent;
	private final Map<LineType, List<Line>> titlePageLines;


	ParserContent(Options options) {
		this.titlePageLines = new HashMap<>();
		parserContent = new ArrayList<>(100);
		this.stats = new ParserContentMetaData(options);
	}


	@Override
	public Line get(int index) {
		if (index >= 0 && index < parserContent.size()) {
			return parserContent.get(index);
		} else {
			return null;
		}
	}

	@Override
	public Line getNext(Line l) {
		int index = l.getLineNr() + 1;
		if (index > 0 && index < parserContent.size()) {
			return parserContent.get(index);
		} else {
			return null;
		}
	}
	public Iterator<Line> iterator() {
		return parserContent.iterator();
	}

	@Override
	public Line getPrev(Line l) {
		int index = l.getLineNr() - 1;
		if (index > 0 && index < parserContent.size()) {
			return parserContent.get(index);
		} else {
			return null;
		}
	}

	@Override
	public int getLineCount() {
		return parserContent.size();
	}

	@Override
	public boolean prevLineIsEmpty(Line l) {
		if (getPrev(l) != null) {
			return getPrev(l).emptyText();
		} else {
			return true;
		}
	}

	@Override
	public boolean nextLineIsEmpty(Line l) {
		if (getNext(l) != null) {
			return getNext(l).emptyText();
		} else {
			return true;
		}
	}

	@Override
	public boolean hasNext(Line l) {
		return getNext(l) != null;
	}

	@Override
	public void remove(Line l) {
		remove(l.getLineNr());
	}

	@Override
	public Map<LineType, List<Line>> getTitlepageLines() {
		return titlePageLines;
	}

	ParserLine addLine(String text) {
		if (!parserContent.isEmpty()) {
			Line lastParserLine = parserContent.getLast();
			int lineNr = lastParserLine.getLineNr() + 1;
			ParserLine l;
			if (text == null || text.isEmpty()) {
				l = new ParserLine(null, lineNr);
			} else {
				l = new ParserLine(text, lineNr);
			}
			parserContent.add(l);
			return l;
		} else {
			ParserLine l;
			if (text == null || text.isEmpty()) {
				l = new ParserLine(null, 0);
			} else {
				l = new ParserLine(text, 0);
			}
			parserContent.add(l);
			return l;
		}

	}

	void addLine(String text, int index) {
		ParserLine l;
		if (text == null || text.isEmpty()) {
			l = new ParserLine(null, index);
		} else {
			l = new ParserLine(text, index);
		}
		if (index >= 0 && index < parserContent.size() - 1) {
			Line nextParserLine = parserContent.get(index);
			incFollowing((ParserLine)nextParserLine);
			parserContent.add(index, l);
		} else if (index == parserContent.size() - 1) {
			addLine(text);
		}
	}

	void remove(int index) {
		if (index >= 0 && index < parserContent.size()) {
			ParserLine l = (ParserLine) parserContent.get(index);
			decFollowing(l);
			parserContent.remove(index);
		}
	}

	private void decFollowing(ParserLine l) {
		int start = l.getLineNr();
		int end = getLineCount();
		for (int i = start; i < end; i++) {
			((ParserLine)parserContent.get(i)).decLineNr();
		}
	}

	private void incFollowing(ParserLine nextParserLine) {
		int start = nextParserLine.getLineNr();
		int end = getLineCount();
		for (int i = start; i < end; i++) {
			((ParserLine)parserContent.get(i)).incLineNr();
		}
	}

	public void addTitlePageLine(LineType t, Line l) {
		List<Line> lines = titlePageLines.computeIfAbsent(t, k -> new ArrayList<>());
		lines.add(l);
	}

	public List<Line> getTitlePageLines(LineType t) {
		return titlePageLines.get(t);
	}

	public boolean containsTitlePageLine(LineType t) {
		return titlePageLines.containsKey(t);
	}

	public boolean titlePageLinesEmpty() {
		return titlePageLines.isEmpty();
	}

	@Override
	public ParserContentMetaData getStats() {
		return stats;
	}
}
