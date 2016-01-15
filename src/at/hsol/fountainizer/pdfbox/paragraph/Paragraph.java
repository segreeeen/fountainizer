package at.hsol.fountainizer.pdfbox.paragraph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import at.hsol.fountainizer.parser.interfaces.ParserType;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.pdfbox.interfaces.Margins;
import at.hsol.fountainizer.pdfbox.pager.AbstractPager;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;

/**
 * @author Lukas Theis
 */
public class Paragraph implements Margins {

    // private String text;
    private RichString richText;
    private AbstractPager<?> pager;
    private ParserType linetype;
    private List<RichString> lines;

    private float marginTop;
    private float marginLeft;
    private float marginRight;
    private float marginBottom = 10.0f;

    private boolean uppercase = false;
    private boolean centered = false;
    private boolean underlined = false;
    private Integer lineTypeNumber;
    private boolean dualDialogue;

    public Paragraph(RichString richString) {
	this.richText = richString;
    }
    
    public Paragraph(LineType type) {
	this.linetype = type;
    }

    public Paragraph(String text) {
	this(new RichString(text, new RichFormat()));
    }

    public void initForPager(AbstractPager<?> abstractPager) throws IOException {
	this.pager = abstractPager;
	this.lines = initLines();
    }

    public  List<RichString> getLines() {
	return lines;
    }
    
    private List<RichString> initLines() throws IOException {
	if (pager == null) {
	    return null;
	}
	
	List<RichString> lines = new LinkedList<RichString>();
	if (linetype == LineType.TRANSITION) {
	    lines.add(richText);
	    return lines;
	}
	String text = this.richText.toString();

	if (isUppercase()) {
	    text = text.toUpperCase();
	    richText.convertToUpperCase();
	}

	int start = 0;

	while (start < text.length()) {
	    int end = start;
	    while (richText.substring(start, end).stringWidth(pager) < getActualPageWidth() && end < text.length()) {
		end++;
	    }
	    int lastSpace = richText.substring(start, end).toString().lastIndexOf(" ");
	    if (end < text.length() && lastSpace > 0) {
		end = start + lastSpace + 1;
	    }

	    lines.add(richText.substring(start, end));
	    start = end;
	}

	return lines;
    }

    public float getActualPageWidth() {
	if (pager == null) {
	    return 0.0f;
	}
	return pager.getPageWidth() - getMarginLeft() - getMarginRight();
    }

    public float getMarginTop() {
	return marginTop;
    }

    public float getMarginLeft() {
	return marginLeft;
    }

    public float getMarginRight() {
	return marginRight;
    }

    public float getMarginBottom() {
	return marginBottom;
    }

    public void setMarginTop(float top) {
	this.marginTop = top;
    }

    public void setMarginLeft(float left) {
	this.marginLeft = left;
    }

    public void setMarginRight(float right) {
	this.marginRight = right;
    }

    public void setMarginBottom(float bottom) {
	this.marginBottom = bottom;
    }

    public void setMargin(float top, float left, float right, float bottom) {
	setMarginTop(top);
	setMarginLeft(left);
	setMarginRight(right);
	setMarginBottom(bottom);
    }

    public boolean isUppercase() {
	return uppercase;
    }

    public void setUppercase(boolean uppercase) {
	this.uppercase = uppercase;
    }

    public boolean isCentered() {
	return centered;
    }

    public void setCentered(boolean centered) {
	this.centered = centered;
    }

    public boolean isUnderlined() {
	return underlined;
    }

    public void setUnderlined(boolean underlined) {
	this.underlined = underlined;
    }

    public ParserType getLinetype() {
	return linetype;
    }

    public void setLinetype(ParserType linetype) {
	this.linetype = linetype;
    }
    
    public static Paragraph getEmptyParagraph() {
	return new Paragraph(LineType.EMPTY);
    }

    public void setLineTypeNumber(int lineTypeNumber) {
	this.lineTypeNumber = lineTypeNumber;	
    }
    
    public Integer getLineTypeNumber() {
	return lineTypeNumber;
    }

    public boolean isDualDialogue() {
	return dualDialogue;
    }

    public void setDualDialogue(boolean dualDialogue) {
	this.dualDialogue = dualDialogue;
    }

}