package at.hsol.fountainizer.pdfbox.pager;

import java.awt.Color;
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
public class PagerController {
	public static class PagerType {
		public static final Class<TitlePager> TITLE_PAGER = TitlePager.class;
		public static final Class<StandardPager> STANDARD_PAGER = StandardPager.class;
		public static final Class<CharacterPager> CHARACTER_PAGER = CharacterPager.class;
		public static final Class<CustomScriptPager> CUSTOM_PAGER = CustomScriptPager.class;
	}

	public enum FormattingType {
		NORMAL, ITALIC, BOLD, BOLDITALIC, UNDERLINED;
	}

	// Page Layout
	static final Color STANDARD_TEXT_COLOR = Color.BLACK;
	static final int STANDARD_FONT_SIZE = 12;
	static final float LINE_HEIGHT_FACTOR = 1.2f;
	static final float UNDER_LINE_FACTOR = 1.1f;
	static final float UNDER_LINE_CORRECTION = 3f;

	// Dual Constants
	protected static final int FIRST = 1;
	protected static final int SECOND = 2;

	// Document
	private PDDocument doc;
	private HashMap<Class<? extends AbstractPager<?>>, AbstractPager<?>> pagers;

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

	public PagerController(float marginLeft, float marginRight, float marginTop, float marginBottom, Options options)
			throws IOException {
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
	 * 
	 * @throws IOException
	 */
	public <T extends AbstractPager<?>> T getPager(Class<T> PAGER_TYPE) throws IOException {
		if (pagers.containsKey(PAGER_TYPE)) {
			return PAGER_TYPE.cast(pagers.get(PAGER_TYPE));
		} else {
			AbstractPager<?> p = PagerFactory.getPager(PAGER_TYPE, this);
			pagers.put(PAGER_TYPE, p);
			return PAGER_TYPE.cast(p);
		}
	}

	public void finalizeDocument(String fileName) throws IOException {
		if (pagers.isEmpty()) {
			throw new IllegalStateException("There are no pagers to be written.");
		}

		if (pagers.containsKey(PagerType.TITLE_PAGER)) {
			TitlePager pager = (TitlePager) pagers.get(PagerType.TITLE_PAGER);
			pager.closeStream();
			PDPageTree pages = pagers.get(PagerType.TITLE_PAGER).getPages();
			pager.closeStream();
			for (PDPage p : pages) {
				doc.addPage(p);
			}
		}

		if (pagers.containsKey(PagerType.CHARACTER_PAGER)) {
			CharacterPager pager = (CharacterPager) pagers.get(PagerType.CHARACTER_PAGER);
			pager.closeStream();
			PDPageTree pages = pagers.get(PagerType.CHARACTER_PAGER).getPages();
			for (PDPage p : pages) {
				doc.addPage(p);
			}
		}

		if (pagers.containsKey(PagerType.STANDARD_PAGER)) {
			StandardPager pager = (StandardPager) pagers.get(PagerType.STANDARD_PAGER);
			pager.closeStream();
			PDPageTree pages = pagers.get(PagerType.STANDARD_PAGER).getPages();
			for (PDPage p : pages) {
				doc.addPage(p);
			}
		}

		if (pagers.containsKey(PagerType.CUSTOM_PAGER)) {
			CustomScriptPager pager = (CustomScriptPager) pagers.get(PagerType.CUSTOM_PAGER);
			pager.closeStream();
			PDPageTree pages = pagers.get(PagerType.CUSTOM_PAGER).getPages();
			for (PDPage p : pages) {
				doc.addPage(p);
			}
		}
		doc.save(fileName);
		doc.close();
		closePagers();
	}

	private void closePagers() throws IOException {
		for (AbstractPager<?> p : pagers.values()) {
			p.close();
		}
	}

}
