package at.hacksolutions.f2p.parser.line;

public interface LinesList extends Iterable<Line>{
    public Line get(int index);
    public Line getNext(Line l);
    public Line getPrev(Line l);
    public int getLineCount();
    public boolean pEmptyText(Line l);
    public boolean nEmptyText(Line l);
    
}

