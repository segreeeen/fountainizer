package at.hsol.fountainizer.parser.interfaces;

import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.content.TitlePage;
import at.hsol.fountainizer.parser.meta.FCharacters;

/**
 * @author Felix Batusic
 */
public interface Content extends Iterable<SimpleLine> {
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
    
    public FCharacters getCharacters();
    
    public void setCharacters(FCharacters characters);

}
