package at.hacksolutions.f2p.pdfbox.interfaces;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;

import at.hacksolutions.f2p.pdfbox.paragraph.Paragraph;

public interface Pager extends Margins {

    public void drawParagraph(Paragraph p) throws IOException;

    public void finalize(String filename)
	    throws IOException;

    public float getLineHeight();

    public float getPageWidth();

    public float getPageHeight();

    public PDFont getFont();

    public PDFont getBoldFont();

    public PDFont getItalicFont();

    public PDFont getBoldItalicFont();

    public int getFontSize();
    
    public PDDocument getDoc();
}
