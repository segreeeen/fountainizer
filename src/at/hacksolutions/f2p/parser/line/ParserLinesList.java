package at.hacksolutions.f2p.parser.line;


public interface ParserLinesList extends Iterable<ParserLine>{
    public void add(ParserLine l);
    
    public ParserLine get(int index);

    public ParserLine getNext(ParserLine l);

    public ParserLine getPrev(ParserLine l);

    public int getLineCount();

    public boolean pEmptyText(ParserLine l);

    public boolean nEmptyText(ParserLine l);
}
