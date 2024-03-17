package at.hsol.fountainizer.core.api.parser;

import java.util.List;
import java.util.Map;

/**
 * @author Felix Batusic
 */
public interface Content extends Iterable<Line> {
    Line get(int index);

    Line getNext(Line l);

    Line getPrev(Line l);

    boolean hasNext(Line l);

    int getLineCount();

    boolean prevLineIsEmpty(Line l);

    boolean nextLineIsEmpty(Line l);

    void remove(Line l);

    Map<LineType, List<Line>> getTitlepageLines();


    Statistics getStats();
}
