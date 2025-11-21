package at.hsol.fountainizer.core.api.parser;

import at.hsol.fountainizer.core.api.types.LineType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Content extends Iterable<Line> {
    Line get(int paramInt);

    Line getFirst();

    Optional<Line> getNext(Line paramLine);

    Optional<Line> getPrev(Line paramLine);

    boolean hasNext(Line paramLine);

    int getLineCount();

    boolean prevLineIsEmpty(Line paramLine);

    boolean nextLineIsEmpty(Line paramLine);

    void remove(Line paramLine);

    Map<at.hsol.fountainizer.core.api.types.LineType, List<Line>> getTitlepageLines();

    Statistics getStats();
}
