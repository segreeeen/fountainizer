package at.hsol.fountainizer.printer.pdf.pager;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.printer.pdf.PdfPrinter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.encoding.Encoding;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

public class PagerController {
	public static class PagerType {
		public static final Class<TitlePager> TITLE_PAGER = TitlePager.class;

		public static final Class<StandardPager> STANDARD_PAGER = StandardPager.class;

		public static final Class<CharacterPager> CHARACTER_PAGER = CharacterPager.class;
	}

	static final Color STANDARD_TEXT_COLOR = Color.BLACK;

	static final int STANDARD_FONT_SIZE = 12;

	static final float LINE_HEIGHT_FACTOR = 1.2F;

	static final float UNDER_LINE_FACTOR = 1.1F;

	static final float UNDER_LINE_CORRECTION = 3.0F;

	protected static final int FIRST = 1;

	protected static final int SECOND = 2;

	private final PDDocument doc;

	private final HashMap<Class<? extends AbstractPager<?>>, AbstractPager<?>> pagers;

	final PDFont font;

	final PDFont boldFont;

	final PDFont italicFont;

	final PDFont boldItalicFont;

	final float marginTop;

	final float marginLeft;

	final float marginRight;

	final float marginBottom;

	final Options options;

	public PagerController(float marginLeft, float marginRight, float marginTop, float marginBottom, Options options) throws IOException {
		this.doc = new PDDocument();
		this.marginLeft = marginLeft;
		this.marginRight = marginRight;
		this.marginTop = marginTop;
		this.marginBottom = marginBottom;
		this.options = options;
		this.font = (PDFont)PDTrueTypeFont.load(this.doc, PdfPrinter.class.getResourceAsStream("fonts/CourierPrime.ttf"), (Encoding)WinAnsiEncoding.INSTANCE);
		this.boldFont = (PDFont)PDTrueTypeFont.load(this.doc, PdfPrinter.class.getResourceAsStream("fonts/CourierPrimeBold.ttf"), (Encoding)WinAnsiEncoding.INSTANCE);
		this.italicFont = (PDFont)PDTrueTypeFont.load(this.doc, PdfPrinter.class.getResourceAsStream("fonts/CourierPrimeItalic.ttf"), (Encoding)WinAnsiEncoding.INSTANCE);
		this.boldItalicFont = (PDFont)PDTrueTypeFont.load(this.doc, PdfPrinter.class.getResourceAsStream("fonts/CourierPrimeBoldItalic.ttf"), (Encoding)WinAnsiEncoding.INSTANCE);
		this.pagers = new HashMap<>();
	}

	public <T extends AbstractPager<?>> T getPager(Class<T> PAGER_TYPE) throws IOException {
		if (this.pagers.containsKey(PAGER_TYPE))
			return PAGER_TYPE.cast(this.pagers.get(PAGER_TYPE));
		AbstractPager<?> p = PagerFactory.getPager(PAGER_TYPE, this);
		this.pagers.put(PAGER_TYPE, p);
		return PAGER_TYPE.cast(p);
	}

    public byte[] createPDF() throws IOException {
        finalizeDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.doc.save(out);
        return out.toByteArray();
    }

    public void createPDF(String fileName) throws IOException {
        finalizeDocument();
        this.doc.save(fileName);
        this.doc.close();
        closePagers();
    }

	private void finalizeDocument() throws IOException {
		if (this.pagers.isEmpty())
			throw new IllegalStateException("There are no pagers to be written.");
		if (this.pagers.containsKey(PagerType.TITLE_PAGER) && this.options.printTitlePage()) {
			TitlePager pager = (TitlePager)this.pagers.get(PagerType.TITLE_PAGER);
			pager.closeStream();
			PDPageTree pages = ((AbstractPager)this.pagers.get(PagerType.TITLE_PAGER)).getPages();
			for (PDPage p : pages)
				this.doc.addPage(p);
		}
		if (this.pagers.containsKey(PagerType.CHARACTER_PAGER) && this.options.printCharacterPage()) {
			CharacterPager pager = (CharacterPager)this.pagers.get(PagerType.CHARACTER_PAGER);
			pager.closeStream();
			PDPageTree pages = ((AbstractPager)this.pagers.get(PagerType.CHARACTER_PAGER)).getPages();
			for (PDPage p : pages)
				this.doc.addPage(p);
		}
		if (this.pagers.containsKey(PagerType.STANDARD_PAGER)) {
			StandardPager pager = (StandardPager)this.pagers.get(PagerType.STANDARD_PAGER);
			pager.closeStream();
			PDPageTree pages = ((AbstractPager)this.pagers.get(PagerType.STANDARD_PAGER)).getPages();
			for (PDPage p : pages)
				this.doc.addPage(p);
		}

	}

	private void closePagers() throws IOException {
		for (AbstractPager<?> p : this.pagers.values())
			p.close();
	}
}
