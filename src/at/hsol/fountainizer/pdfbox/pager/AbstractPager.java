package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDFont;

import at.hsol.fountainizer.pdfbox.interfaces.Pager;
import at.hsol.fountainizer.pdfbox.pager.PageController.PagerType;

public abstract class AbstractPager<T> implements Pager {
    enum Alignment {
	LEFT, CENTERED, RIGHT;	
    }
    
    protected PagerType type;  
    protected PageController controller;
    
    protected PDDocument document;
    protected PDPage currentPage;
    protected PDPageContentStream stream;
    
    protected int fontSize;
    protected float yPos;    
    protected float xPos;  
    
    AbstractPager(PageController controller) {
	this.controller = controller;
	this.fontSize = PageController.STANDARD_FONT_SIZE;
    }
    
    PDPageTree getPages() {
	return document.getPages();
    }
    
    void nextPage() throws IOException {
	if (currentPage != null) {
	    stream.close();
	}

	currentPage = new PDPage();
	document.addPage(currentPage);
	stream = new PDPageContentStream(document, currentPage);
	yPos = 0;
    }
    
    abstract void printContent(T t);

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
	return controller.font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 
		* PageController.STANDARD_FONT_SIZE * PageController.LINE_HEIGHT_FACTOR;
    }
    
    public float getLineHeight(int fontSize) {
	return controller.font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 
		* fontSize * PageController.LINE_HEIGHT_FACTOR;
    }

    public float getPageWidth() {
	return currentPage.getMediaBox().getWidth() - getMarginLeft() - getMarginRight();
    }

    public float getPageHeight() {
	return currentPage.getMediaBox().getHeight() - getMarginTop() - getMarginBottom();
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
    
}
