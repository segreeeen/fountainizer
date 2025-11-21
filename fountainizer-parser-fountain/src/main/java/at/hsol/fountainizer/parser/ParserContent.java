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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

class ParserContent implements Content {
    private final ArrayList<Line> parserContent = new ArrayList<>(1000);

    private final ParserContentMetaData stats;

    private final Map<LineType, List<Line>> titlePageLines;

    Function<ParserLine, ParserLine> getPreviousLineFunction;

    Function<ParserLine, ParserLine> getNextLineFunction;
    private AtomicInteger currentLineNumber = new AtomicInteger(0);
    private int titleOffset = 0;

    public Line get(int index) {
        int indexOffset = index + titleOffset;
        if (indexOffset >= 0 && indexOffset < this.parserContent.size())
            return this.parserContent.get(indexOffset);
        return null;
    }

    @Override
    public Line getFirst() {
        return get(0);
    }

    public Optional<Line> getNext(Line l) {
        return this.getIfPresent(l.getLineNr() + 1);
    }

    public Iterator<Line> iterator() {
        return this.parserContent.iterator();
    }

    public Optional<Line> getPrev(Line l) {
        return this.getIfPresent(l.getLineNr() - 1);
    }

    private Optional<Line> getIfPresent(int i) {
        if (i >= this.parserContent.size())
            return Optional.empty();
        if (i < 0) return Optional.empty();

        return Optional.ofNullable(this.parserContent.get(i));
    }


    public int getLineCount() {
        return this.parserContent.size() - titleOffset;
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
        l.setLineNr(this.currentLineNumber.getAndIncrement());
        l.setGetPrevLineFunction(this.getPreviousLineFunction);
        l.setGetNextLineFunction(this.getNextLineFunction);
        return l;
    }

    void remove(int index) {
        if (index >= 0 && index < this.parserContent.size()) {
            ParserLine l = (ParserLine) this.parserContent.get(index);
            decFollowing(l);
            this.parserContent.remove(index);
        }
    }

    private void decFollowing(ParserLine l) {
        int start = l.getLineNr();
        int end = getLineCount();
        for (int i = start; i < end; i++)
            ((ParserLine) this.parserContent.get(i)).decLineNr();
    }

    private void incFollowing(ParserLine nextParserLine) {
        int start = nextParserLine.getLineNr();
        int end = getLineCount();
        for (int i = start; i < end; i++)
            ((ParserLine) this.parserContent.get(i)).incLineNr();
    }

    public void addTitlePageLine(LineType t, Line l) {
        List<Line> lines = this.titlePageLines.computeIfAbsent(t, k -> new ArrayList());
        lines.add(l);
    }

    public ParserContentMetaData getStats() {
        return this.stats;
    }

    ParserContent(Options options) {
        this.getPreviousLineFunction = (line ->
                (ParserLine) getPrev(line).orElse(null));
        this.getNextLineFunction = (line ->
                (ParserLine) getNext(line).orElse(null));
        this.titlePageLines = new HashMap<>();
        this.stats = new ParserContentMetaData(options);
    }

    public void removeAll(List<ParserLine> titleParserLines) {
        this.parserContent.removeAll(titleParserLines);
    }

    public void setTitleOffset(int titleLineCount) {
        this.titleOffset = titleLineCount + 1;
    }
}
