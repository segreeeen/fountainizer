package at.hsol.fountainizer.pdfbox.pager;
import java.io.IOException;
import java.util.List;

import at.hsol.fountainizer.parser.meta.FCharacter;

public class CharacterPager extends AbstractPager<List<FCharacter>> {
    Integer fontSize;
    
    CharacterPager(PagerController controller) throws IOException {
	super(controller);
	super.type = PagerController.PagerType.CHARACTER_PAGER;
	this.fontSize = null;
    }

    @Override
    public void printContent(List<FCharacter> t) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public int getFontSize() {
	if (fontSize != null) {
	    return fontSize;
	} else {
	    return PagerController.STANDARD_FONT_SIZE;
	}
    }



}
