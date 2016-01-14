package at.hsol.fountainizer.pdfbox.pager;

import at.hsol.fountainizer.pdfbox.pager.PageController.PagerType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

public class TitlePager extends AbstractPager<Paragraph> {
    Integer fontSize;

    TitlePager(PageController controller) {
	super(controller);
	super.type = PagerType.TITLE_PAGER;
	this.fontSize = null;
    }

    @Override
    void printContent(Paragraph t) {
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
