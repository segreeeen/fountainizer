package at.hacksolutions.f2p.pdfbox;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.encoding.EncodingManager;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.encoding.Encoding;

public class Pager implements I_HasMargin {
	private PDDocument doc;
	private PDPage page;
	private PDPageContentStream stream;

	private PDFont font;

	public PDFont getFont() {
		return font;
	}

	private PDFont boldFont;

	public PDFont getBoldFont() {
		return boldFont;
	}

	private PDFont italicFont;

	public PDFont getItalicFont() {
		return italicFont;
	}
	

	private PDFont boldItalicFont;

	public PDFont getBoldItalicFont() {
		return boldItalicFont;
	}

	public int getFontSize() {
		return fontSize;
	}

	private int fontSize = 14;
	private float writtenAreaY = 0;
	private float lineHeightFactor = 1.2f;
	private float underLineFactor = 1.1f;

	private float marginTop;
	private float marginLeft;
	private float marginRight;
	private float marginBottom;

	public Pager(PDDocument doc, float top, float left, float right,
			float bottom) throws IOException {
		setMargin(top, left, right, bottom);
		this.doc = doc;

		font = PDType1Font.COURIER;
		boldFont = PDType1Font.COURIER_BOLD;
		italicFont = PDType1Font.COURIER_OBLIQUE;
		boldItalicFont = PDType1Font.COURIER_BOLD_OBLIQUE;
		
		Encoding enc = new EncodingManager().getEncoding(COSName.WIN_ANSI_ENCODING);
		
		font.setFontEncoding(enc);
		boldFont.setFontEncoding(enc);
		italicFont.setFontEncoding(enc);
		boldItalicFont.setFontEncoding(enc);

		initNextPage();
	}

	private void initNextPage() throws IOException {

		if (page != null) {
			stream.close();
		}

		this.page = new PDPage();
		doc.addPage(page);
		stream = new PDPageContentStream(doc, page);
		writtenAreaY = 0;
	}

	public void finalize(String filename) throws IOException, COSVisitorException {
		stream.close();
		doc.save(filename);
		doc.close();
	}

	public void drawParagraph(Paragraph p) throws IOException {
		p.initForPager(this);
		writtenAreaY = writtenAreaY + p.getMarginTop();
		for (RichString text : p.getLines()) {

			if (writtenAreaY + getLineHeight() > getPageHeight()) {
				initNextPage();
			}

			float x;
			if (p.isCentered()) {
				x = getMarginLeft()
						+ p.getMarginLeft()
						+ ((p.getPageWidthRespectingMargins() - text
								.stringWidth(this)) / 2);
			} else {
				x = getMarginLeft() + p.getMarginLeft();
			}
			float y = page.getMediaBox().getHeight() - getMarginTop()
					- writtenAreaY;
			
			float xOfRow = 0.0f;
			
			for (RichFormat rowPart : text.getFormattings()) {

				stream.beginText();
				stream.setNonStrokingColor(Color.BLACK);
				
				stream.setFont(rowPart.selectFont(this), fontSize);

				stream.moveTextPositionByAmount(x+xOfRow, y);
				stream.drawString(rowPart.getText());
				stream.endText();
				if (rowPart.isUnderline()) {
					stream.addLine(x + xOfRow, y + getUnderLineDifference(),
							x+ xOfRow + rowPart.stringWidth(this), y
									+ getUnderLineDifference());
				}
				
				xOfRow = xOfRow + rowPart.stringWidth(this);
				

			}

			if (p.isUnderlined()) {
				stream.addLine(x, y + getUnderLineDifference(),
						x + text.stringWidth(this), y
								+ getUnderLineDifference());
			}
			writtenAreaY = writtenAreaY + getLineHeight();

			stream.stroke();

		}
		writtenAreaY = writtenAreaY + p.getMarginBottom();
	}

	public float getLineHeight() {
		return font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000
				* fontSize * lineHeightFactor;
	}

	private float getUnderLineDifference() {
		return font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000
				* fontSize * underLineFactor - getLineHeight();
	}

	public float getPageWidth() {
		return page.getMediaBox().getWidth() - getMarginLeft()
				- getMarginRight();
	}

	public float getPageHeight() {
		return page.getMediaBox().getHeight() - getMarginTop()
				- getMarginBottom();
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



}
