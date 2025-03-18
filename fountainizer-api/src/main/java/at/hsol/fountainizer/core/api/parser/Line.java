package at.hsol.fountainizer.core.api.parser;

import at.hsol.fountainizer.core.api.types.HeadingType;
import at.hsol.fountainizer.core.api.types.LineType;

/**
 * @author Felix Batusic
 */
public interface Line {

    LineType getLineType();

    HeadingType getHeadingType();

    int getLineNr();

    boolean emptyText();

    boolean isDualDialogue();

    int getLineTypeNumber();

    String getText();

    Line getPrev();

    Line getNext();

    boolean hasNext();

    boolean hasPrev();

    StylizedText getRichString();
}
