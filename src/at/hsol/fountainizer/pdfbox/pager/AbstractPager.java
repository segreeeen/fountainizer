package at.hsol.fountainizer.pdfbox.pager;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import at.hsol.fountainizer.pdfbox.interfaces.Pager;

public abstract class AbstractPager<T> implements Pager {
    enum Alignment {
	LEFT, CENTERED, RIGHT;
    }

    protected Class<? extends AbstractPager<?>> type;
    protected PagerController controller;

    protected PDDocument document;
    protected PDPage currentPage;
    protected PDPageContentStream stream;

    protected Integer fontSize;
    protected float yPos;
    protected float xPos;
    protected float curY;

    AbstractPager(PagerController controller) throws IOException {
	this.controller = controller;
	this.fontSize = null;
	this.document = new PDDocument();
	this.nextPage();
	this.xPos = getMarginLeft();
	this.yPos = getPageHeight() - getMarginTop();
    }

    PDDocument getDoc() {
	return this.document;
    }

    void nextPage() throws IOException {
	if (currentPage != null) {
	    if (controller.options.printPageNumber()) {
		printPageNumber(getPageWidth() / 2);
	    }
	    stream.close();
	}

	this.currentPage = new PDPage();
	this.document.addPage(currentPage);
	this.stream = new PDPageContentStream(document, currentPage);
	this.yPos = getPageHeight() - getMarginTop();
    }

    void nextLine(Float marginTop) throws IOException {
	if (marginTop != null) {
	    this.xPos = getMarginLeft();
	    this.yPos -= (getLineHeight() + marginTop - PagerController.UNDER_LINE_CORRECTION);
	} else {
	    this.xPos = getMarginLeft();
	    this.yPos -= (getLineHeight() - PagerController.UNDER_LINE_CORRECTION);
	}
    }

    void nextLine() throws IOException {
	nextLine(null);
    }

    void closeStream() throws IOException {
	if (this.type == PagerController.PagerType.STANDARD_PAGER && controller.options.printPageNumber()) {
	    printPageNumber(getPageWidth() / 2);
	}
	stream.stroke();
	stream.close();
    }

    void close() throws IOException {
	this.document.close();
    }

    void finishLine(float marginBottom) {
	yPos -= marginBottom;
    }

    protected void printString(String s, float x, float y, PDFont font, int fontSize, Color color) throws IOException {
	stream.beginText();
	setTextOptions(x, y, font, fontSize, color);
	stream.showText(s);
	stream.endText();
	stream.stroke();
    }

    protected boolean yExceeded(float heightAddition) {
	if ((yPos - getMarginBottom() - getLineHeight() - heightAddition) < 0) {
	    return true;
	} else {
	    return false;
	}
    }

    protected boolean yExceeded() {
	return yExceeded(0);
    }

    protected boolean xExceeded(float stringWidth, float marginRight) {
	if ((xPos + stringWidth + getMarginRight() + marginRight) >= getPageWidth()) {
	    return true;
	} else {
	    return false;
	}
    }

    protected boolean xExceeded(float stringWidth) {
	return xExceeded(stringWidth, 0);
    }

    protected float getUnderLineDifference() {
	return controller.font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * getFontSize() * PagerController.UNDER_LINE_FACTOR - getLineHeight();
    }

    public abstract void printContent(T t) throws IOException;

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AbstractPager<?> other = (AbstractPager<?>) obj;
	if (type != other.type)
	    return false;
	return true;
    }

    public float getLineHeight() {
	return controller.font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * PagerController.STANDARD_FONT_SIZE * PagerController.LINE_HEIGHT_FACTOR;
    }

    public float getLineHeight(int fontSize) {
	return controller.font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize * PagerController.LINE_HEIGHT_FACTOR;
    }

    public float getPageCenter() {
	return getPageWidth() / 2;
    }

    public float getPageWidth() {
	return currentPage.getMediaBox().getWidth() - getMarginLeft() - getMarginRight();
    }

    public float getPageHeight() {
	return currentPage.getMediaBox().getHeight();
    }

    public float getMarginTop() {
	return controller.marginTop;
    }

    public float getMarginLeft() {
	return controller.marginLeft;
    }

    public float getMarginRight() {
	return controller.marginRight;
    }

    public float getMarginBottom() {
	return controller.marginBottom;
    }

    public PDFont getFont() {
	return controller.font;
    }

    public PDFont getBoldFont() {
	return controller.boldFont;
    }

    public PDFont getItalicFont() {
	return controller.italicFont;
    }

    public PDFont getBoldItalicFont() {
	return controller.boldItalicFont;
    }

    public int getFontSize() {
	if (fontSize != null) {
	    return fontSize;
	} else {
	    return PagerController.STANDARD_FONT_SIZE;
	}
    }

    public void underline(float x, float y, float x2, float y2) throws IOException {
	stream.setLineWidth(0.1f);
	stream.moveTo(x, y);
	stream.lineTo(x2, y2);
	stream.stroke();
    }

    private void printPageNumber(float x) throws IOException {
	printString(Integer.toString(document.getNumberOfPages()), getMarginLeft() + x, getMarginBottom() - (getLineHeight()), getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
    }

    private void setTextOptions(float x, float y, PDFont font, int fontSize, Color color) throws IOException {
	stream.newLineAtOffset(x, y);
	stream.setNonStrokingColor(Color.BLACK);
	stream.setFont(font, fontSize);
    }

}
