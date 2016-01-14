package at.hsol.fountainizer.pdfbox.pager;
import java.util.List;

import at.hsol.fountainizer.parser.data.FCharacter;

public class CharacterPager extends AbstractPager<List<FCharacter>> {
    Integer fontSize;
    
    CharacterPager(PageController controller) {
	super(controller);
	super.type = PageController.PagerType.CHARACTER_PAGER;
	this.fontSize = null;
    }

    @Override
    void printContent(List<FCharacter> t) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public int getFontSize() {
	if (fontSize != null) {
	    return fontSize;
	} else {
	    return PageController.STANDARD_FONT_SIZE;
	}
    }


}
