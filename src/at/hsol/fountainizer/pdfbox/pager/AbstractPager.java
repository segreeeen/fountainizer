package at.hsol.fountainizer.pdfbox.pager;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.fonts.Fonts;
import at.hsol.fountainizer.pdfbox.interfaces.Pager;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

/**
 * @author Lukas Theis
 * @author Felix Batusic
 */
public abstract class AbstractPager implements Pager {

    // DocSpec
    protected PDDocument doc;
    protected PDPage page;
    protected PDPageContentStream stream;

    // Fonts
    protected PDFont font;
    private PDFont boldFont;
    private PDFont italicFont;
    private PDFont boldItalicFont;

    // Page Layout
    protected int fontSize = 12;
    protected float writtenAreaY = 0;
    private float lineHeightFactor = 1.2f;
    private float underLineFactor = 1.1f;
    private float underLineDifference;

    // Margins
    private float marginTop;
    private float marginLeft;
    private float marginRight;
    private float marginBottom;

    // Options
    protected final Options options;
    
    //Dual Constants
    protected static final int FIRST = 1;
    protected static final int SECOND = 2;

    public AbstractPager(PDDocument doc, float top, float left, float right, float bottom, Options options) throws IOException, URISyntaxException {
	setMargin(top, left, right, bottom);
	this.doc = doc;

	font = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrime.ttf"));
	boldFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeBold.ttf"));
	italicFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeItalic.ttf"));
	boldItalicFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeBoldItalic.ttf"));
	underLineDifference = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize * underLineFactor - getLineHeight();
	this.options = options;
    }

    public AbstractPager(PDDocument doc, float top, float left, float right, float bottom) throws IOException, URISyntaxException {
	this(doc, top, left, right, bottom, null);
    }

    public void initNextPage() throws IOException {

	if (page != null) {
	    stream.close();
	}

	this.page = new PDPage();
	doc.addPage(page);
	stream = new PDPageContentStream(doc, page);
	writtenAreaY = 0;
    }

    public void finalize(String filename) throws IOException {
	stream.close();
	doc.save(filename);
	doc.close();
    }

    public float getLineHeight() {
	return font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize * lineHeightFactor;
    }
    
    public float getLineHeight(int fontSize) {
	return font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize * lineHeightFactor;
    }

    public float getPageWidth() {
	return page.getMediaBox().getWidth() - getMarginLeft() - getMarginRight();
    }

    public float getPageHeight() {
	return page.getMediaBox().getHeight() - getMarginTop() - getMarginBottom();
    }

    public float getMarginTop() {
	return marginTop;
    }

    public float getMarginLeft() {
	return marginLeft;
    }

    public float getMarginRight() {
	return marginRight;
    }

    public float getMarginBottom() {
	return marginBottom;
    }

    public void setMarginTop(float top) {
	this.marginTop = top;
    }

    public void setMarginLeft(float left) {
	this.marginLeft = left;
    }

    public void setMarginRight(float right) {
	this.marginRight = right;
    }

    public void setMarginBottom(float bottom) {
	this.marginBottom = bottom;
    }

    public PDFont getFont() {
	return font;
    }

    public PDFont getBoldFont() {
	return boldFont;
    }

    public PDFont getItalicFont() {
	return italicFont;
    }

    public PDFont getBoldItalicFont() {
	return boldItalicFont;
    }

    public int getFontSize() {
	return fontSize;
    }

    public PDDocument getDoc() {
	return doc;
    }

    public void setMargin(float top, float left, float right, float bottom) {
	setMarginTop(top);
	setMarginLeft(left);
	setMarginRight(right);
	setMarginBottom(bottom);
    }

    protected float getUnderLineDifference() {
	return underLineDifference;
    }
    
    public void printParagraph(Paragraph p, boolean dual) throws IOException {
	if (p.getLinetype() == LineType.EMPTY) {
	    writtenAreaY = (writtenAreaY + getLineHeight());
	    return;
	}

	p.initForPager(this);
	writtenAreaY = (writtenAreaY + p.getMarginTop()) - 2;
	List<RichString> lines = p.getLines();
	// float linesHeight = getLineHeight()*(p.getLines().size());
	for (RichString text : lines) {
	    if (writtenAreaY + getLineHeight() > getPageHeight()) {
		initNextPage();
	    }

	    float x;
	    if (p.isCentered()) {
		x = getMarginLeft() + p.getMarginLeft() + ((p.getPageWidthRespectingMargins() - text.stringWidth(this)) / 2);
	    } else if (p.getLinetype() == LineType.TRANSITION) {
		x = getMarginRight();
	    } else {
		x = getMarginLeft() + p.getMarginLeft();
	    }

	    float y = page.getMediaBox().getHeight() - getMarginTop() - writtenAreaY;

	    float currentLineWidth = 0.0f;
	    if (p.getLinetype() == LineType.TRANSITION) {
		LinkedList<RichFormat> trformats = text.getFormattings();
		ListIterator<RichFormat> li = trformats.listIterator(trformats.size());

		// Print transition right aligned
		while (li.hasPrevious()) {
		    RichFormat f = li.previous();
		    printRightAligned(f, this.getPageWidth() - x, y, currentLineWidth);
		}

	    } else {
		for (RichFormat rowPart : text.getFormattings()) {
		    Integer ltn = p.getLineTypeNumber();
		    if (ltn != null && p.getLinetype() == LineType.CHARACTER) {
			printLineTypeNumber(y, ltn, dual);
		    }
		    printLeftAligned(rowPart, x, y, currentLineWidth);
		    if (rowPart.isUnderline()) {
			stream.setLineWidth(0.1f);
			stream.moveTo(x + currentLineWidth, y + getUnderLineDifference());
			stream.lineTo(x + currentLineWidth + rowPart.stringWidth(this), y + getUnderLineDifference());
			stream.stroke();
		    }
		    currentLineWidth = currentLineWidth + rowPart.stringWidth(this);
		}
	    }
	    if (p.isUnderlined()) {
		stream.moveTo(x, y + getUnderLineDifference());
		stream.lineTo(x + text.stringWidth(this), y + getUnderLineDifference());
		stream.stroke();
	    }
	    writtenAreaY = writtenAreaY + getLineHeight();
	    stream.stroke();
	}
	writtenAreaY = (writtenAreaY + p.getMarginBottom()) - 2;

    }
    
    public void printParagraph(Paragraph p) throws IOException {
	printParagraph(p, false);
    }

    protected void printLeftAligned(RichFormat rowPart, float x, float y, float currentLineWidth, Integer fontSize) throws IOException {
	int originalFontSize = this.fontSize;
	if (fontSize != null) {
	    this.fontSize = fontSize;
	}
	stream.beginText();
	stream.newLineAtOffset(x + currentLineWidth, y);
	stream.setNonStrokingColor(Color.BLACK);
	stream.setFont(rowPart.selectFont(this), this.fontSize);
	stream.showText(rowPart.getText());
	stream.endText();
	stream.stroke();
	if (fontSize != null) {
	    this.fontSize = originalFontSize;
	}
    }
    
    protected void printLeftAligned(RichFormat rowPart, float x, float y, float currentLineWidth) throws IOException {
	printLeftAligned(rowPart, x, y, currentLineWidth, null);
    }
    
    protected void printRightAligned(RichFormat rowPart, float x, float y, float currentLineWidth) throws IOException {
	float text_width = (font.getStringWidth(rowPart.getText()) / 1000.0f) * fontSize;
	stream.beginText();
	stream.newLineAtOffset(x - text_width, y);
	stream.setFont(rowPart.selectFont(this), fontSize);
	stream.showText(rowPart.getText());
	stream.endText();
	stream.stroke();
    }

    protected void printLineTypeNumber(float y, Integer ltn, boolean dual) throws IOException {
	if (options != null) {
	    if (this.options.printTakeNumbers()) {
		stream.beginText();
		if (!dual) {
		    stream.newLineAtOffset(getMarginLeft() + LineType.LINENUMBER.getMarginLeft(), y);
		} else {
		    stream.newLineAtOffset(getMarginLeft() + (getPageWidth() / 2) + LineType.LINENUMBER.getMarginLeft(), y);
		}
	    	stream.showText(ltn.toString());
	    	stream.endText();
	    	stream.stroke();
	    }
	}
    }
    
    protected float getDualValue(Float f, int order) {
	if (order == FIRST) {
	    return (f / 2);
	} else if (order == SECOND) {
	    return (getPageWidth() / 2) + (f / 2);
	}

	// not allowed to happen. just don't let that happen.
	return 0f;
    }

}
