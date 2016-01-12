package at.hsol.fountainizer.pdfbox.pager;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import at.hsol.fountainizer.pdfbox.PagerOptions;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.fonts.Fonts;
import at.hsol.fountainizer.pdfbox.interfaces.Pager;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;

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
    private float lineHeight;
    private float underLineDifference;

    // Margins
    private float marginTop;
    private float marginLeft;
    private float marginRight;
    private float marginBottom;

    // Options
    protected final PagerOptions options;

    public AbstractPager(PDDocument doc, float top, float left, float right, float bottom, PagerOptions options) throws IOException, URISyntaxException {
	setMargin(top, left, right, bottom);
	this.doc = doc;

	font = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrime.ttf"));
	boldFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeBold.ttf"));
	italicFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeItalic.ttf"));
	boldItalicFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeBoldItalic.ttf"));

	lineHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize * lineHeightFactor;
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
	return lineHeight;
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

    protected void printLeftAligned(RichFormat rowPart, float x, float y, float currentLineWidth) throws IOException {
	stream.beginText();
	stream.newLineAtOffset(x + currentLineWidth, y);
	stream.setNonStrokingColor(Color.BLACK);
	stream.setFont(rowPart.selectFont(this), fontSize);
	stream.showText(rowPart.getText());
	stream.endText();
    }

    protected void printRightAligned(RichFormat rowPart, float x, float y, float currentLineWidth) throws IOException {
	float text_width = (font.getStringWidth(rowPart.getText()) / 1000.0f) * fontSize;
	stream.beginText();
	stream.newLineAtOffset(x - text_width, y);
	stream.setFont(rowPart.selectFont(this), fontSize);
	stream.showText(rowPart.getText());
	stream.endText();
    }

    protected void printLineTypeNumber(float y, Integer ltn) throws IOException {
	if (options != null) {
	    if (this.options.printTakeNumbers()) {
		stream.beginText();
	    	stream.newLineAtOffset(getMarginLeft() + LineType.LINENUMBER.getMarginLeft(), y);
	    	stream.showText(ltn.toString());
	    	stream.endText();
	    }
	}
    }

}
