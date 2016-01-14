package at.hsol.fountainizer.pdfbox.pager;

import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

public class StandardPager extends AbstractPager<Paragraph> {
    Integer fontSize;
    
    StandardPager(PageController controller) { 
	super(controller);
	super.type = PageController.PagerType.STANDARD_PAGER;
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
