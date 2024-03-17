package at.hsol.fountainizer.printer.pdf.pager;

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
