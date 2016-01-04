package at.hsol.fountainizer.pdfbox.interfaces;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;

import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

/**
 * @author Lukas Theis
 * @author Felix Batusic
 */
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
