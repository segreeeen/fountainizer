package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.parser.Line;
import at.hsol.fountainizer.core.api.parser.Statistics;
import at.hsol.fountainizer.core.api.types.LineType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

class ParserContent implements Content {
	private final ArrayList<Line> parserContent = new ArrayList<>(1000);

	private final ParserContentMetaData stats;

	private final Map<LineType, List<Line>> titlePageLines;

	Function<Integer, ParserLine> getPreviousLineFunction;

	Function<Integer, ParserLine> getNextLineFunction;

	public Line get(int index) {
		if (index >= 0 && index < this.parserContent.size())
			return this.parserContent.get(index);
		return null;
	}

	public Optional<Line> getNext(Line l) {
		return Optional.ofNullable(l.getNext());
	}

	public Iterator<Line> iterator() {
		return this.parserContent.iterator();
	}

	public Optional<Line> getPrev(Line l) {
		return Optional.ofNullable(l.getPrev());
	}

	public int getLineCount() {
		return this.parserContent.size();
	}

	public boolean prevLineIsEmpty(Line l) {
		if (l.hasPrev())
			return l.getPrev().emptyText();
		return true;
	}

	public boolean nextLineIsEmpty(Line l) {
		if (l.hasNext())
			return l.getNext().emptyText();
		return true;
	}

	public boolean hasNext(Line l) {
		return l.hasNext();
	}

	public void remove(Line l) {
		remove(l.getLineNr());
	}

	public Map<LineType, List<Line>> getTitlepageLines() {
		return this.titlePageLines;
	}

	ParserLine addLine(String text) {
		String setText = text.isEmpty() ? null : text;
		ParserLine l = new ParserLine(setText);
		this.parserContent.add(l);
		l.setLineNr(this.parserContent.size());
		l.setGetPrevLineFunction(this.getPreviousLineFunction);
		l.setGetNextLineFunction(this.getNextLineFunction);
		return l;
	}

	void remove(int index) {
		if (index >= 0 && index < this.parserContent.size()) {
			ParserLine l = (ParserLine)this.parserContent.get(index);
			decFollowing(l);
			this.parserContent.remove(index);
		}
	}

	private void decFollowing(ParserLine l) {
		int start = l.getLineNr();
		int end = getLineCount();
		for (int i = start; i < end; i++)
			((ParserLine)this.parserContent.get(i)).decLineNr();
	}

	private void incFollowing(ParserLine nextParserLine) {
		int start = nextParserLine.getLineNr();
		int end = getLineCount();
		for (int i = start; i < end; i++)
			((ParserLine)this.parserContent.get(i)).incLineNr();
	}

	public void addTitlePageLine(LineType t, Line l) {
		List<Line> lines = this.titlePageLines.computeIfAbsent(t, k -> new ArrayList());
		lines.add(l);
	}

	public ParserContentMetaData getStats() {
		return this.stats;
	}

	ParserContent(Options options) {
		this.getPreviousLineFunction = (lineNum ->
				(lineNum.intValue() - 2 >= 0 && lineNum.intValue() < this.parserContent.size()) ? (ParserLine)this.parserContent.get(lineNum.intValue() - 2) : null);
		this.getNextLineFunction = (lineNum ->
				(lineNum.intValue() >= 0 && lineNum.intValue() < this.parserContent.size()) ? (ParserLine)this.parserContent.get(lineNum.intValue()) : null);
		this.titlePageLines = new HashMap<>();
		this.stats = new ParserContentMetaData(options);
	}
}
