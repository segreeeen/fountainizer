package at.hsol.fountainizer.core.parser.api;

import at.hsol.fountainizer.core.parser.FountainEmphasizedText;

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

    FountainEmphasizedText getRichString();
}
