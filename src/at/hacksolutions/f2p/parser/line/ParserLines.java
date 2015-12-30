package at.hacksolutions.f2p.parser.line;

public interface ParserLines extends Iterable<SimpleLine>{
    public SimpleLine get(int index);
    public SimpleLine getNext(SimpleLine l);
    public SimpleLine getPrev(SimpleLine l);
    public int getLineCount();
    public boolean pEmptyText(SimpleLine l);
    public boolean nEmptyText(SimpleLine l);
    
}

