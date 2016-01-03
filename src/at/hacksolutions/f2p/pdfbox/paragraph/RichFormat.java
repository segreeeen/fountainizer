package at.hacksolutions.f2p.pdfbox.paragraph;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.font.PDFont;

import at.hacksolutions.f2p.pdfbox.interfaces.Pager;

/**
 * @author Lukas Theis
 */
public class RichFormat {

    private boolean underline = false;
    private boolean bold = false;
    private boolean italic = false;
    private String text = "";

    public RichFormat() {
	this(false, false, false);
    }

    public RichFormat(boolean underline, boolean bold, boolean italic) {
	setUnderline(underline);
	setBold(bold);
	setItalic(italic);
    }

    public RichFormat cloneWithNewText(String text) {
	RichFormat clone = new RichFormat(underline, bold, italic);
	clone.setText(text);
	return clone;

    }

    public boolean isUnderline() {
	return underline;
    }

    public void setUnderline(boolean underline) {
	this.underline = underline;
    }

    public boolean isBold() {
	return bold;
    }

    public void setBold(boolean bold) {
	this.bold = bold;
    }

    public boolean isItalic() {
	return italic;
    }

    public void setItalic(boolean italic) {
	this.italic = italic;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public PDFont selectFont(Pager page) throws IOException {
	if (isBold() && isItalic()) {
	    return page.getBoldItalicFont();
	} else if (isBold()) {
	    return page.getBoldFont();
	} else if (isItalic()) {
	    return page.getItalicFont();
	} else {
	    return page.getFont();
	}
    }

    public float stringWidth(Pager page) throws IOException {
	return selectFont(page).getStringWidth(getText()) / 1000
		* page.getFontSize();
    }

    @Override
    public String toString() {
	return text + ", Bold = " + bold + ", Italic = " + italic + ", Underlined = " + underline + "\n";
    }
}
