package at.hsol.fountainizer.core.parser;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.core.parser.api.Content;
import at.hsol.fountainizer.core.parser.api.Line;
import at.hsol.fountainizer.core.parser.api.LineType;

import java.util.*;

/**
 * @author Felix Batusic
 */
class ParserContent implements Content {
	private final ParserContentMetaData stats;
	private final ArrayList<ParserLine> parserContent;
	private final Map<LineType, List<Line>> titlePageLines;


	ParserContent(Options options) {
		this.titlePageLines = new HashMap<>();
		parserContent = new ArrayList<>(100);
		this.stats = new ParserContentMetaData(options);
	}

	@Override
	public Iterator<ParserLine> iterator() {
		return parserContent.iterator();
	}

	@Override
	public ParserLine get(int index) {
		if (index >= 0 && index < parserContent.size()) {
			return parserContent.get(index);
		} else {
			return null;
		}
	}

	@Override
	public ParserLine getNext(ParserLine l) {
		int index = l.getLineNr() + 1;
		if (index > 0 && index < parserContent.size()) {
			return parserContent.get(index);
		} else {
			return null;
		}
	}

	@Override
	public ParserLine getPrev(ParserLine l) {
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
	public boolean prevLineIsEmpty(ParserLine l) {
		if (getPrev(l) != null) {
			return getPrev(l).emptyText();
		} else {
			return true;
		}
	}

	@Override
	public boolean nextLineIsEmpty(ParserLine l) {
		if (getNext(l) != null) {
			return getNext(l).emptyText();
		} else {
			return true;
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
	public Map<LineType, List<Line>> getTitlepageLines() {
		return titlePageLines;
	}

	ParserLine addLine(String text) {
		if (!parserContent.isEmpty()) {
			ParserLine lastParserLine = parserContent.getLast();
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
			ParserLine nextParserLine = parserContent.get(index);
			incFollowing(nextParserLine);
			parserContent.add(index, l);
		} else if (index == parserContent.size() - 1) {
			addLine(text);
		}
	}

	void remove(int index) {
		if (index >= 0 && index < parserContent.size()) {
			ParserLine l = parserContent.get(index);
			decFollowing(l);
			parserContent.remove(index);
		}
	}

	private void decFollowing(ParserLine l) {
		int start = l.getLineNr();
		int end = getLineCount();
		for (int i = start; i < end; i++) {
			parserContent.get(i).decLineNr();
		}
	}

	private void incFollowing(ParserLine nextParserLine) {
		int start = nextParserLine.getLineNr();
		int end = getLineCount();
		for (int i = start; i < end; i++) {
			parserContent.get(i).incLineNr();
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
