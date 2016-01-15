package at.hsol.fountainizer.pdfbox.pager;

import java.awt.Color;
import java.io.IOException;

import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichFormat;

public class StandardPager extends AbstractPager<Paragraph> {
    Integer fontSize;
    
    StandardPager(PageController controller) { 
	super(controller);
	super.type = PageController.PagerType.STANDARD_PAGER;
	this.fontSize = null;
    }

    @Override
    void printContent(Paragraph t) {
	// TODO Auto-generated method stub
	
    }
    
    protected void printLeftAligned(RichFormat rowPart, float x, float y, float currentLineWidth) throws IOException {
	stream.beginText();
	stream.newLineAtOffset(x + currentLineWidth, y);
	stream.setNonStrokingColor(Color.BLACK);
	stream.setFont(rowPart.selectFont(this), this.fontSize);
	stream.showText(rowPart.getText());
	stream.endText();
	stream.stroke();
    }
    
    protected void printRightAligned(RichFormat rowPart, float x, float y, float currentLineWidth) throws IOException {
	float text_width = (rowPart.stringWidth(this) / 1000.0f) * fontSize;
	stream.beginText();
	stream.newLineAtOffset(x - text_width, y);
	stream.setFont(rowPart.selectFont(this), fontSize);
	stream.showText(rowPart.getText());
	stream.endText();
	stream.stroke();
    }
    
    protected void printCentered(RichFormat rowPart, float x, float y, float currentLineWidth) throws IOException {
	float text_width = (rowPart.stringWidth(this) / 1000.0f) * fontSize;
	stream.beginText();
	stream.newLineAtOffset(x - text_width, y);
	stream.setFont(rowPart.selectFont(this), fontSize);
	stream.showText(rowPart.getText());
	stream.endText();
	stream.stroke();
    }

    @Override
    public int getFontSize() {
	if (fontSize != null) {
	    return fontSize;
	} else {
	    return PageController.STANDARD_FONT_SIZE;
	}
    }
}
