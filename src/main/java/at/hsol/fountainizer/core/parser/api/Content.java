package at.hsol.fountainizer.core.parser.api;

import at.hsol.fountainizer.core.parser.ParserLine;

import java.util.List;
import java.util.Map;

/**
 * @author Felix Batusic
 */
public interface Content extends Iterable<ParserLine> {
    ParserLine get(int index);

    ParserLine getNext(ParserLine l);

    ParserLine getPrev(ParserLine l);

    boolean hasNext(ParserLine l);

    int getLineCount();

    boolean prevLineIsEmpty(ParserLine l);

    boolean nextLineIsEmpty(ParserLine l);

    void remove(ParserLine l);

    Map<LineType, List<Line>> getTitlepageLines();


    Statistics getStats();
}
