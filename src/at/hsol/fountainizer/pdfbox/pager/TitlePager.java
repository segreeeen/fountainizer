package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.util.List;

import at.hsol.fountainizer.parser.content.TitlePage;
import at.hsol.fountainizer.parser.types.TitlePageType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

public class TitlePager extends AbstractPager<TitlePage> {
    private static final int TITLE_SIZE = 18;

    private float titleY;
    private float rightY;
    private float centeredY;
    private float leftY;

    TitlePager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
	super(controller);
	super.type = type;
	this.fontSize = null;
	this.titleY = getPageHeight() - (getPageHeight() / 5f);
	this.rightY = getPageHeight() - (getPageHeight() / 4f) * 3;
	this.centeredY = getPageHeight() - (getPageHeight() / 4f);
	this.leftY = getPageHeight() - (getPageHeight() / 4) * 3;
    }

    @Override
    public void printContent(TitlePage t) throws IOException {
	if (t.contains(TitlePageType.CENTERED)) {
	    List<Paragraph> paragraphs = t.getParagraphForPDF(TitlePageType.CENTERED);
	    printParagraphs(paragraphs, TitlePageType.CENTERED);
	}

	if (t.contains(TitlePageType.LEFT)) {
	    List<Paragraph> paragraphs = t.getParagraphForPDF(TitlePageType.LEFT);
	    printParagraphs(paragraphs, TitlePageType.LEFT);
	}

	if (t.contains(TitlePageType.RIGHT)) {
	    List<Paragraph> paragraphs = t.getParagraphForPDF(TitlePageType.RIGHT);
	    printParagraphs(paragraphs, TitlePageType.RIGHT);
	}

	if (t.contains(TitlePageType.TITLE)) {
	    List<Paragraph> paragraphs = t.getParagraphForPDF(TitlePageType.TITLE);
	    printParagraphs(paragraphs, TitlePageType.TITLE);
	}

    }

    private void printParagraphs(List<Paragraph> paragraphs, TitlePageType t) throws IOException {
	for (Paragraph p : paragraphs) {
	    if (t == TitlePageType.CENTERED) {
		printCentered(p);
		centeredY -= p.getMarginBottom();
	    } else if (t == TitlePageType.LEFT) {
		printLeft(p);
	    } else if (t == TitlePageType.RIGHT) {
		printRight(p);
	    } else if (t == TitlePageType.TITLE) {
		fontSize = TITLE_SIZE;
		printTitle(p);
		centeredY -= getLineHeight();
		fontSize = null;
	    }
	}
    }

    private void printTitle(Paragraph p) throws IOException {
	p.initForPager(this);
	List<RichString> sList = p.getLines();
	for (RichString s : sList) {
	    float currentLineWidth = 0f;
	    float x = getMarginLeft() + p.getMarginLeft() + ((p.getActualPageWidth() - s.stringWidth(this)) / 2);
	    for (RichFormat f : s.getFormattings()) {
		printString(f.getText(), currentLineWidth + x, titleY, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
		currentLineWidth += f.stringWidth(this);
	    }
	    titleY -= getLineHeight();
	    centeredY -= getLineHeight();
	}
    }

    private void printRight(Paragraph p) throws IOException {
	p.initForPager(this);
	List<RichString> sList = p.getLines();
	for (RichString s : sList) {
	    float currentLineWidth = 0f;
	    for (RichFormat f : s.getFormattings()) {
		printString(f.getText(), super.xPos + p.getMarginLeft() + currentLineWidth, rightY, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
		currentLineWidth += f.stringWidth(this);
	    }
	    rightY -= getLineHeight();
	}

    }

    private void printLeft(Paragraph p) throws IOException {
	p.initForPager(this);
	List<RichString> sList = p.getLines();
	for (RichString s : sList) {
	    float currentLineWidth = 0f;
	    for (RichFormat f : s.getFormattings()) {
		printString(f.getText(), super.xPos + currentLineWidth, leftY, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
		currentLineWidth += f.stringWidth(this);
	    }
	    leftY -= getLineHeight();
	}

    }

    private void printCentered(Paragraph p) throws IOException {
	p.initForPager(this);
	List<RichString> sList = p.getLines();
	for (RichString s : sList) {
	    float currentLineWidth = 0f;
	    float x = getMarginLeft() + ((p.getActualPageWidth() / 2) - (s.stringWidth(this) / 2));
	    for (RichFormat f : s.getFormattings()) {
		printString(f.getText(), currentLineWidth + x, centeredY, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
		currentLineWidth += f.stringWidth(this);
	    }
	    centeredY -= getLineHeight();
	}

    }

    void nextLine(TitlePageType t) {

    }

}
