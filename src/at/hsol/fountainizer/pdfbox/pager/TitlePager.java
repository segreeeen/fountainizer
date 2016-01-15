package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;

import at.hsol.fountainizer.pdfbox.pager.PagerController.PagerType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

public class TitlePager extends AbstractPager<Paragraph> {
    Integer fontSize;

    TitlePager(PagerController controller) throws IOException {
	super(controller);
	super.type = PagerType.TITLE_PAGER;
	this.fontSize = null;
    }

    @Override
    public void printContent(Paragraph t) {
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
