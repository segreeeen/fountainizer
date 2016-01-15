package at.hsol.fountainizer.parser.interfaces;

import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.content.TitlePage;
import at.hsol.fountainizer.parser.meta.Characters;

/**
 * @author Felix Batusic
 */
public interface ParserList extends Iterable<SimpleLine> {
    public SimpleLine get(int index);

    public SimpleLine getNext(SimpleLine l);

    public SimpleLine getPrev(SimpleLine l);

    public boolean hasNext(SimpleLine l);

    public int getLineCount();

    public boolean pEmptyText(SimpleLine l);

    public boolean nEmptyText(SimpleLine l);

    public void remove(SimpleLine l);

    public TitlePage getTitlepage();

    public void setTitlepage(TitlePage tp);
    
    public Characters getCharacters();
    
    public void setCharacters(Characters characters);

}
