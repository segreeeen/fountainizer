package at.hsol.fountainizer.pdfbox.pager;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDFont;

import at.hsol.fountainizer.pdfbox.interfaces.Pager;
import at.hsol.fountainizer.pdfbox.pager.PagerController.PagerType;

public abstract class AbstractPager<T> implements Pager {
    enum Alignment {
	LEFT, CENTERED, RIGHT;
    }

    protected PagerType type;
    protected PagerController controller;

    protected PDDocument document;
    protected PDPage currentPage;
    protected PDPageContentStream stream;

    protected int fontSize;
    protected float yPos;
    protected float xPos;
    protected float curY;

    AbstractPager(PagerController controller) throws IOException {
	this.controller = controller;
	this.fontSize = PagerController.STANDARD_FONT_SIZE;
	this.document = new PDDocument();
	this.nextPage();
	this.xPos = getMarginLeft();
	this.yPos = getPageHeight();
    }

    PDPageTree getPages() {
	return document.getPages();
    }

    void nextPage() throws IOException {
	if (currentPage != null) {
	    stream.close();
	}

	this.currentPage = new PDPage();
	this.document.addPage(currentPage);
	this.stream = new PDPageContentStream(document, currentPage);
	this.yPos = getPageHeight()-getMarginTop();
    }

    void nextLine(Float marginTop) {
	if (marginTop != null) {
	    this.yPos -= marginTop;
	}
	xPos = getMarginLeft();
	yPos -= getLineHeight() - PagerController.UNDER_LINE_CORRECTION; 
    }

    void nextLine() {
	nextLine(null);
    }
    
    void closeStream() throws IOException {
	stream.stroke();
	stream.close();
    }
    
    void close() throws IOException {
	this.document.close();
    }


    protected void printLeftAligned(String s, float x, float y, PDFont font, int fontSize, Color color) throws IOException {
	stream.beginText();
	setTextOptions(x, y, font, fontSize, color);
	stream.showText(s);
	stream.endText();
	stream.stroke();
    }

    protected void printRightAligned(String s, float sWidth, float x, float y, PDFont font, int fontSize, Color color) throws IOException {
	stream.beginText();
	setTextOptions(x - sWidth - getMarginRight(), y, font, fontSize, color);
	stream.showText(s);
	stream.endText();
	stream.stroke();
    }

    protected void printCentered(String s, float sWidth, float x, float y, PDFont font, int fontSize, Color color) throws IOException {
	stream.beginText();
	setTextOptions(getPageCenter() - sWidth, y, font, fontSize, color);
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

    abstract public int getFontSize();

    public void finishLine(float marginBottom) {
	yPos -= marginBottom;
    }
    
    private void setTextOptions(float x, float y, PDFont font, int fontSize, Color color) throws IOException {
	stream.newLineAtOffset(x, y);
	stream.setNonStrokingColor(Color.BLACK);
	stream.setFont(font, fontSize);
    }


}
