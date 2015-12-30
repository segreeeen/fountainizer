package at.hacksolutions.f2p.pdfbox;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.font.PDFont;

public interface Pager extends I_HasMargin {

    public void drawParagraph(Paragraph p) throws IOException;

    public void finalize(String filename)
	    throws IOException, COSVisitorException;

    public float getLineHeight();

    public float getPageWidth();

    public float getPageHeight();

    public PDFont getFont();

    public PDFont getBoldFont();

    public PDFont getItalicFont();

    public PDFont getBoldItalicFont();

    public int getFontSize();
    
}
