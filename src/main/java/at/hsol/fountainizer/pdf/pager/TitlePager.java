package at.hsol.fountainizer.pdf.pager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import at.hsol.fountainizer.core.parser.api.Line;
import at.hsol.fountainizer.core.parser.api.LineType;
import at.hsol.fountainizer.core.parser.api.StylizedText;
import at.hsol.fountainizer.core.parser.api.StylizedTextPart;
import at.hsol.fountainizer.pdf.content.PDFTitlePageWrapper;
import at.hsol.fountainizer.pdf.content.Paragraph;

public class TitlePager extends AbstractPager<Map<LineType, List<Line>>> {
	private static final int TITLE_SIZE = 18;

	private float titleY;
	private float rightY;
	private float centeredY;
	private float leftY;

	TitlePager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
		super(controller);
		super.type = type;
		this.fontSize = null;
		this.titleY = getPageHeight() - (getPageHeight() / 5f);
		this.rightY = getPageHeight() - (getPageHeight() / 4f) * 3;
		this.centeredY = getPageHeight() - (getPageHeight() / 4f);
		this.leftY = getPageHeight() - (getPageHeight() / 4) * 3.5f;
	}

	@Override
	public void printContent(Map<LineType, List<Line>> page) throws IOException {

		PDFTitlePageWrapper pdfPage = new PDFTitlePageWrapper(page);

		if (pdfPage.contains(LineType.TITLEPAGE_CENTERED)) {
			List<Paragraph> paragraphs = pdfPage.getParagraphForPDF(LineType.TITLEPAGE_CENTERED);
			printParagraphs(paragraphs, LineType.TITLEPAGE_CENTERED);
		}

		if (pdfPage.contains(LineType.TITLEPAGE_LEFT)) {
			List<Paragraph> paragraphs = pdfPage.getParagraphForPDF(LineType.TITLEPAGE_LEFT);
			printParagraphs(paragraphs, LineType.TITLEPAGE_LEFT);
		}

		if (pdfPage.contains(LineType.TITLEPAGE_RIGHT)) {
			List<Paragraph> paragraphs = pdfPage.getParagraphForPDF(LineType.TITLEPAGE_RIGHT);
			printParagraphs(paragraphs, LineType.TITLEPAGE_RIGHT);
		}

		if (pdfPage.contains(LineType.TITLEPAGE_TITLE)) {
			List<Paragraph> paragraphs = pdfPage.getParagraphForPDF(LineType.TITLEPAGE_TITLE);
			printParagraphs(paragraphs, LineType.TITLEPAGE_TITLE);
		}

	}

	private void printParagraphs(List<Paragraph> paragraphs, LineType t) throws IOException {
		for (Paragraph p : paragraphs) {
			if (t == LineType.TITLEPAGE_CENTERED) {
				printCentered(p);
				centeredY -= p.getMarginBottom();
			} else if (t == LineType.TITLEPAGE_LEFT) {
				printLeft(p);
			} else if (t == LineType.TITLEPAGE_RIGHT) {
				printRight(p);
			} else if (t == LineType.TITLEPAGE_TITLE) {
				fontSize = TITLE_SIZE;
				printTitle(p);
				centeredY -= getLineHeight();
				fontSize = null;
			}
		}
	}

	private void printTitle(Paragraph p) throws IOException {
		p.initForPager(this);
		List<StylizedText> sList = p.getLines();
		for (StylizedText s : sList) {
			float currentLineWidth = 0f;
			float x = (super.getAbsoluteWidth() / 2) - (PagerUtils.stringWidth(this, s) / 2) - p.getMarginLeft();
			for (StylizedTextPart f : s.getStylizedTextParts()) {
				printString(f.getText(), currentLineWidth + x, titleY, getFont(), getFontSize(),
						PagerController.STANDARD_TEXT_COLOR);
				currentLineWidth += PagerUtils.formatWidth(this, f);
			}
			titleY -= getLineHeight();
			centeredY -= getLineHeight();
		}
	}

	private void printRight(Paragraph p) throws IOException {
		p.initForPager(this);
		List<StylizedText> sList = p.getLines();
		for (StylizedText s : sList) {
			float currentLineWidth = 0f;
			for (StylizedTextPart f : s.getStylizedTextParts()) {
				printString(f.getText(), super.xPos + p.getMarginLeft() + currentLineWidth, rightY, getFont(),
						getFontSize(), PagerController.STANDARD_TEXT_COLOR);
				currentLineWidth += PagerUtils.formatWidth(this, f);
			}
			rightY -= getLineHeight();
		}

	}

	private void printLeft(Paragraph p) throws IOException {
		p.initForPager(this);
		List<StylizedText> sList = p.getLines();
		for (StylizedText s : sList) {
			float currentLineWidth = 0f;
			for (StylizedTextPart f : s.getStylizedTextParts()) {
				printString(f.getText(), super.xPos + currentLineWidth, leftY, getFont(), getFontSize(),
						PagerController.STANDARD_TEXT_COLOR);
				currentLineWidth += PagerUtils.formatWidth(this, f);
			}
			leftY -= getLineHeight();
		}

	}

	private void printCentered(Paragraph p) throws IOException {
		p.initForPager(this);
		List<StylizedText> sList = p.getLines();
		for (StylizedText s : sList) {
			float currentLineWidth = 0f;
			float x = (super.getAbsoluteWidth() / 2) - (PagerUtils.stringWidth(this,s) / 2) - p.getMarginLeft();
			for (StylizedTextPart f : s.getStylizedTextParts()) {
				printString(f.getText(), currentLineWidth + x, centeredY, getFont(), getFontSize(),
						PagerController.STANDARD_TEXT_COLOR);
				currentLineWidth += PagerUtils.formatWidth(this, f);
			}
			centeredY -= getLineHeight();
		}
	}

}
