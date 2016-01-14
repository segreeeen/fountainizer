package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.pdfbox.fonts.Fonts;

/**
 * This class controls all the printing for the various Pagetypes. Serves as an
 * API for PDF Printing in fountainizer.
 * 
 * @author Felix Batusic
 *
 */
public class PageController {
    public enum PagerType {
	TITLE_PAGER, STANDARD_PAGER, CHARACTER_PAGER;
    }

    // Page Layout
    public static final int STANDARD_FONT_SIZE = 12;
    public static final float LINE_HEIGHT_FACTOR = 1.2f;
    public static final float UNDER_LINE_FACTOR = 1.1f;

    // Dual Constants
    // TODO: move them to appropriate PageBuilder class.
    protected static final int FIRST = 1;
    protected static final int SECOND = 2;

    // Document
    private PDDocument doc;
    private HashMap<PagerType, AbstractPager<?>> pagers;

    // Fonts
    final PDFont font;
    final PDFont boldFont;
    final PDFont italicFont;
    final PDFont boldItalicFont;

    // Margins
    final float marginTop;
    final float marginLeft;
    final float marginRight;
    final float marginBottom;

    // Options
    final Options options;

    public PageController(float marginLeft, float marginRight, float marginTop, float marginBottom, Options options) throws IOException {
	// set values defined by user
	this.doc = new PDDocument();
	this.marginLeft = marginLeft;
	this.marginRight = marginRight;
	this.marginTop = marginTop;
	this.marginBottom = marginBottom;
	this.options = options;

	// initialize standard values.
	// note that it should be possible to set a custom font at a later time.
	this.font = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrime.ttf"));
	this.boldFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeBold.ttf"));
	this.italicFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeItalic.ttf"));
	this.boldItalicFont = PDType0Font.load(doc, Fonts.class.getResourceAsStream("CourierPrimeBoldItalic.ttf"));

	this.pagers = new HashMap<>();
    }

    /**
     * There can only be one Pager of a type at a time. Please see
     * PageController.PagerType for the possible Types.
     */
    public AbstractPager<?> getPager(PagerType PAGER_TYPE) {
	if (pagers.containsKey(PAGER_TYPE)) {
	    return pagers.get(PAGER_TYPE);
	} else {
	    AbstractPager<?> p = PagerFactory.getPager(PAGER_TYPE, this);
	    pagers.put(PAGER_TYPE, p);
	    return p;
	}
    }

    /**
     * Saves the document. For the Pagers following order is applied: Titlepage,
     * Characterpage, Standardpage
     * 
     * @throws IOException
     */
    public void finalizeDocument(String fileName) throws IOException {
	if (pagers.isEmpty()) {
	    throw new IllegalStateException("There are no pagers to be written.");
	}
	
	if (pagers.containsKey(PagerType.TITLE_PAGER)) {
	    PDPageTree pages = pagers.get(PagerType.TITLE_PAGER).getPages();
	    for (PDPage p: pages) {
		doc.addPage(p);
	    }
	} else if (pagers.containsKey(PagerType.CHARACTER_PAGER)) {
	    PDPageTree pages = pagers.get(PagerType.CHARACTER_PAGER).getPages();
	    for (PDPage p: pages) {
		doc.addPage(p);
	    }
	} else if (pagers.containsKey(PagerType.STANDARD_PAGER)) {
	    PDPageTree pages = pagers.get(PagerType.STANDARD_PAGER).getPages();
	    for (PDPage p: pages) {
		doc.addPage(p);
	    }
	}
	doc.save(fileName);
	doc.close();
    }

    

}
