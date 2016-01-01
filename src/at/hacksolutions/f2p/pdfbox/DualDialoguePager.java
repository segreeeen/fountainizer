package at.hacksolutions.f2p.pdfbox;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public class DualDialoguePager extends AbstractPager {
    private boolean isSecondLine;
    private int leftX;
    private int rightX;

    public DualDialoguePager(StandardPager p) throws IOException {
	super(p.getDoc(), p.getMarginTop(), p.getMarginLeft(), p.getMarginRight(), p.getMarginBottom());
	super.page = p.page;
	super.stream = p.stream;
	super.fontSize = p.fontSize;
	super.writtenAreaY = page.getMediaBox().getHeight();
	isSecondLine = false;
	
    }

    @Override
    public void drawParagraph(Paragraph p) throws IOException {
	// TODO Auto-generated method stub
	
    }

}
