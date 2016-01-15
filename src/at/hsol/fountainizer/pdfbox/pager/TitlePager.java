package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.util.List;

import at.hsol.fountainizer.parser.content.TitlePage;
import at.hsol.fountainizer.parser.types.TitlePageType;
import at.hsol.fountainizer.pdfbox.pager.PagerController.PagerType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

public class TitlePager extends AbstractPager<TitlePage> {
    Integer fontSize;

    TitlePager(PagerController controller) throws IOException {
	super(controller);
	super.type = PagerType.TITLE_PAGER;
	this.fontSize = null;
    }

    @Override
    public void printContent(TitlePage t) throws IOException {
	if (t.contains(TitlePageType.CENTERED)) {
	    List<Paragraph> paragraphs = t.getParagraphForPDF(TitlePageType.CENTERED);
	} 
	
	if (t.contains(TitlePageType.LEFT)) {
	    List<Paragraph> paragraphs = t.getParagraphForPDF(TitlePageType.LEFT);
	}
	
	if (t.contains(TitlePageType.RIGHT)) {
	    List<Paragraph> paragraphs = t.getParagraphForPDF(TitlePageType.RIGHT);
	}
	
	if (t.contains(TitlePageType.TITLE)) {
	    List<Paragraph> paragraphs = t.getParagraphForPDF(TitlePageType.TITLE);
	}
	
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
