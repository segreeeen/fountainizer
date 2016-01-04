package at.hsol.fountainizer.parser.interfaces;

import at.hsol.fountainizer.parser.line.SimpleLine;
import at.hsol.fountainizer.parser.line.TitlePage;

/**
 * @author Felix Batusic
 */
public interface ParserLines extends Iterable<ParserLine> {
    public ParserLine get(int index);

    public ParserLine getNext(ParserLine l);

    public ParserLine getPrev(ParserLine iterator);

    public boolean hasNext(ParserLine l);

    public int getLineCount();

    public boolean pEmptyText(SimpleLine l);

    public boolean nEmptyText(SimpleLine l);

    public void remove(ParserLine l);

    public void add(TitlePage tp, int i);

    public TitlePage getTitlepage();

    public void setTitlepage(TitlePage tp);

}