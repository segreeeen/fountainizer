package at.hacksolutions.f2p.parser.line;

import java.util.Iterator;

public interface ParserLinesList extends Iterator<ParserLine> {
    public ParserLine get(int index);

    public ParserLine getNext(ParserLine l);

    public ParserLine getPrev(ParserLine l);

    public int getLineCount();

    public boolean pEmptyText(ParserLine l);

    public boolean nEmptyText(ParserLine l);
}
