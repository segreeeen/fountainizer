package at.hsol.fountainizer.pdfbox.pager;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.font.PDFont;

public interface Pager {

    public float getLineHeight();

    public float getLineHeight(int fontSize);

    public float getPageCenter();

    public float getPageWidth();

    public float getAbsoluteWidth();

    public float getPageHeight();

    public float getMarginTop();

    public float getMarginLeft();

    public float getMarginRight();

    public float getMarginBottom();

    public PDFont getFont();

    public PDFont getBoldFont();

    public PDFont getItalicFont();

    public PDFont getBoldItalicFont();

    public int getFontSize();

    public Color getColor();

    public void setColor(Color color);

    public void underline(float x, float y, float x2, float y2) throws IOException;
}
