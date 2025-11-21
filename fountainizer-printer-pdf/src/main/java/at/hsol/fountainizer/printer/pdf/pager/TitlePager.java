package at.hsol.fountainizer.printer.pdf.pager;

import at.hsol.fountainizer.core.api.parser.Line;
import at.hsol.fountainizer.core.api.parser.StylizedText;
import at.hsol.fountainizer.core.api.parser.StylizedTextPart;
import at.hsol.fountainizer.core.api.types.LineType;
import at.hsol.fountainizer.printer.pdf.content.Paragraph;
import at.hsol.fountainizer.printer.pdf.content.PdfTitlePageWrapper;
import at.hsol.fountainizer.printer.pdf.pager.AbstractPager;
import at.hsol.fountainizer.printer.pdf.pager.PagerController;
import at.hsol.fountainizer.printer.pdf.pager.PagerUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TitlePager
		extends AbstractPager<Map<LineType, List<Line>>> {
	private static final int TITLE_SIZE = 18;
	private float titleY;
	private float rightY;
	private float centeredY;
	private float leftY;

	TitlePager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
		super(controller);
		this.type = type;
		this.fontSize = null;
		this.titleY = this.getPageHeight() - this.getPageHeight() / 5.0f;
		this.rightY = this.getPageHeight() - this.getPageHeight() / 4.0f * 3.0f;
		this.centeredY = this.getPageHeight() - this.getPageHeight() / 4.0f;
		this.leftY = this.getPageHeight() - this.getPageHeight() / 4.0f * 3.5f;
	}

	@Override
	public void printContent(Map<LineType, List<Line>> page) throws IOException {
		List<Paragraph> paragraphs;
		PdfTitlePageWrapper pdfPage = new PdfTitlePageWrapper(page);
		if (pdfPage.contains(LineType.TITLEPAGE_CENTERED)) {
			paragraphs = pdfPage.getParagraphForPDF(LineType.TITLEPAGE_CENTERED);
			this.printParagraphs(paragraphs, LineType.TITLEPAGE_CENTERED);
		}
		if (pdfPage.contains(LineType.TITLEPAGE_LEFT)) {
			paragraphs = pdfPage.getParagraphForPDF(LineType.TITLEPAGE_LEFT);
			this.printParagraphs(paragraphs, LineType.TITLEPAGE_LEFT);
		}
		if (pdfPage.contains(LineType.TITLEPAGE_RIGHT)) {
			paragraphs = pdfPage.getParagraphForPDF(LineType.TITLEPAGE_RIGHT);
			this.printParagraphs(paragraphs, LineType.TITLEPAGE_RIGHT);
		}
		if (pdfPage.contains(LineType.TITLEPAGE_TITLE)) {
			paragraphs = pdfPage.getParagraphForPDF(LineType.TITLEPAGE_TITLE);
			this.printParagraphs(paragraphs, LineType.TITLEPAGE_TITLE);
		}
	}

	private void printParagraphs(List<Paragraph> paragraphs, LineType t) throws IOException {
		for (Paragraph p : paragraphs) {
			if (t == LineType.TITLEPAGE_CENTERED) {
				this.printCentered(p);
				this.centeredY -= p.getMarginBottom();
				continue;
			}
			if (t == LineType.TITLEPAGE_LEFT) {
				this.printLeft(p);
				continue;
			}
			if (t == LineType.TITLEPAGE_RIGHT) {
				this.printRight(p);
				continue;
			}
			if (t != LineType.TITLEPAGE_TITLE) continue;
			this.fontSize = 18;
			this.printTitle(p);
			this.centeredY -= this.getLineHeight();
			this.fontSize = null;
		}
	}

	private void printTitle(Paragraph p) throws IOException {
		p.initForPager(this);
		List<StylizedText> sList = p.getLines();
		for (StylizedText s : sList) {
			float currentLineWidth = 0.0f;
			float x = super.getAbsoluteWidth() / 2.0f - PagerUtils.stringWidth(this, s) / 2.0f - p.getMarginLeft();
			for (StylizedTextPart f : s.getStylizedTextParts()) {
				this.printString(f.getText(), currentLineWidth + x, this.titleY, this.getFont(), this.getFontSize(), PagerController.STANDARD_TEXT_COLOR);
				currentLineWidth += PagerUtils.formatWidth(this, f);
			}
			this.titleY -= this.getLineHeight();
			this.centeredY -= this.getLineHeight();
		}
	}

	private void printRight(Paragraph p) throws IOException {
		p.initForPager(this);
		List<StylizedText> sList = p.getLines();
		for (StylizedText s : sList) {
			float currentLineWidth = 0.0f;
			for (StylizedTextPart f : s.getStylizedTextParts()) {
				this.printString(f.getText(), this.xPos + p.getMarginLeft() + currentLineWidth, this.rightY, this.getFont(), this.getFontSize(), PagerController.STANDARD_TEXT_COLOR);
				currentLineWidth += PagerUtils.formatWidth(this, f);
			}
			this.rightY -= this.getLineHeight();
		}
	}

	private void printLeft(Paragraph p) throws IOException {
		p.initForPager(this);
		List<StylizedText> sList = p.getLines();
		for (StylizedText s : sList) {
			float currentLineWidth = 0.0f;
			for (StylizedTextPart f : s.getStylizedTextParts()) {
				this.printString(f.getText(), this.xPos + currentLineWidth, this.leftY, this.getFont(), this.getFontSize(), PagerController.STANDARD_TEXT_COLOR);
				currentLineWidth += PagerUtils.formatWidth(this, f);
			}
			this.leftY -= this.getLineHeight();
		}
	}

	private void printCentered(Paragraph p) throws IOException {
		p.initForPager(this);
		List<StylizedText> sList = p.getLines();
		for (StylizedText s : sList) {
			float currentLineWidth = 0.0f;
			float x = super.getAbsoluteWidth() / 2.0f - PagerUtils.stringWidth(this, s) / 2.0f - p.getMarginLeft();
			for (StylizedTextPart f : s.getStylizedTextParts()) {
				this.printString(f.getText(), currentLineWidth + x, this.centeredY, this.getFont(), this.getFontSize(), PagerController.STANDARD_TEXT_COLOR);
				currentLineWidth += PagerUtils.formatWidth(this, f);
			}
			this.centeredY -= this.getLineHeight();
		}
	}
}

