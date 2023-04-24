package at.hsol.fountainizer.pdfbox.interfaces;

import org.apache.pdfbox.pdmodel.font.PDFont;

public interface Pager {

    float getLineHeight();

    float getPageWidth();

    float getPageHeight();

    PDFont getFont();

    PDFont getBoldFont();

    PDFont getItalicFont();

    PDFont getBoldItalicFont();

    int getFontSize();

}
