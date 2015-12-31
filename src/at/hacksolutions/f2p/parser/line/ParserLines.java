package at.hacksolutions.f2p.parser.line;

public interface ParserLines extends Iterable<SimpleLine>{
    public SimpleLine get(int index);
    public ParserLine getNext(ParserLine l);
    public ParserLine getPrev(ParserLine iterator);
    public int getLineCount();
    public boolean pEmptyText(SimpleLine l);
    public boolean nEmptyText(SimpleLine l);
    
}

