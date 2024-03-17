package at.hsol.fountainizer.printer.pdf.pager;

import at.hsol.fountainizer.core.api.parser.*;
import at.hsol.fountainizer.printer.pdf.content.PDFLineWrapper;
import at.hsol.fountainizer.printer.pdf.content.Paragraph;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class StandardPager extends AbstractPager<Content> {
	private enum Dual {
		FIRST, SECOND
	}

	private final Integer fontSize;
	protected Dual currentDual = null;
	protected Float nextY = null;
	protected Float originalY;

	StandardPager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
		super(controller);
		super.type = type;
		this.fontSize = null;
	}

	void nextLine(Paragraph p) throws IOException {
		if (controller.options.printTakeNumber() && p.getLinetype() == LineType.CHARACTER) {
			printTakeNumber(p.getLineTypeNumber());
		}
		super.nextLine();
	}

	@Override
	public void printContent(Content parserContent) throws IOException {
		for (Line line : parserContent) {
			PDFLineWrapper pdfLine = new PDFLineWrapper(line);
			LinkedList<Paragraph> paragraphs = pdfLine.getParagraphForPDF();
			if (paragraphs != null) {
				for (Paragraph p : paragraphs) {
					printParagraph(p);
				}
			}
		}
	}

	public void printParagraph(Paragraph p) throws IOException {
		// go to next line, if this is a newline
		if (p.getLinetype() == LineType.EMPTY) {
			super.nextLine();
			return;
		}

		// if this line is a character, we don't want the dialogue to be split
		// over two pages, so we check if there is enough space for at least 3
		// more lines. if there isn't we get next page
		if (p.getLinetype() == LineType.CHARACTER && !p.isDualDialogue()) {
			if (super.yExceeded(getLineHeight() * 3)) {
				nextPage();
			}
		}

		if (super.yExceeded()) {
			nextPage();
		}

		// take care of dual dialogues
		if (p.isDualDialogue()) {
			setDualDialogue(p);
			remarginDual(p);
		}

		// initializes a list of lines with strings fitting the width of the
		// actualPageWidth()
		p.initForPager(this);

		List<StylizedText> lines = p.getLines();

		for (StylizedText rs : lines) {
			setColor(null);
			if (p.getLinetype() == LineType.CHARACTER && super.controller.options.customCharacterScript()) {
				if (p.getRichString().getRawText().equals(controller.options.getCustomCharacter().toLowerCase())) {
					setColor(Color.RED);
				}
			}

			if (p.getLinetype() == LineType.PAGEBREAK) {
				super.nextPage();
				continue;
			}

			float stringWith = PagerUtils.stringWidth(this, rs);

			if (p.isCentered()) { // print centered
				super.xPos = (super.getAbsoluteWidth() / 2) - (stringWith / 2) - p.getMarginLeft();
			}

			if (p.getLinetype() == LineType.TRANSITION) {
				super.xPos = (p.getActualPageWidth() - stringWith - p.getMarginRight());
			}

			List<? extends StylizedTextPart> formats = rs.getStylizedTextParts();
			float currentLineWidth = 0f;
			for (StylizedTextPart rf : formats) {
				if (rf.isUnderline()) { // underline line if underlined
					printLeftAligned(rf, super.xPos + currentLineWidth + p.getMarginLeft(), super.yPos, true);
				} else {
					printLeftAligned(rf, super.xPos + currentLineWidth + p.getMarginLeft(), super.yPos);
				}
				currentLineWidth += PagerUtils.formatWidth(this, rf);
			}
			// return yPos to position if left (first) dialogue was longer than
			// right (second)
			if (nextY != null && p.getLinetype() == LineType.DIALOGUE && nextY < yPos) {
				yPos = nextY;
				nextY = null;
				currentDual = null;
				originalY = null;
			} else {
				nextLine(p);
			}
		}

		super.finishLine(p.getMarginBottom()); // finish paragraph

	}

	@Override
	public int getFontSize() {
		if (fontSize != null) {
			return fontSize;
		} else {
			return PagerController.STANDARD_FONT_SIZE;
		}
	}

	protected void setDualDialogue(Paragraph p) {
		if (p.getLinetype() == LineType.CHARACTER) {
			if (currentDual == null) {
				originalY = super.yPos;
				currentDual = Dual.FIRST;
			} else if (currentDual == Dual.FIRST) {
				nextY = yPos;
				super.yPos = originalY;
				originalY = null;
				currentDual = Dual.SECOND;
			}
		}
	}

	protected void remarginDual(Paragraph p) {
		if (currentDual == Dual.FIRST) {
			p.setMarginLeft(getDualValue(getMarginLeft() + p.getMarginLeft()) - 40);
			p.setMarginRight(getPageWidth() / 2);
		} else if (currentDual == Dual.SECOND) {
			p.setMarginLeft(getDualValue(getMarginLeft() + p.getMarginLeft()));
			p.setMarginRight(p.getActualPageWidth());
		}
	}

	private float getDualValue(Float f) {
		if (currentDual == Dual.FIRST) {
			return (f / 2);
		} else if (currentDual == Dual.SECOND) {
			return (getPageWidth() / 2) + (f / 2);
		} else {
			throw new IllegalStateException(
					"\nIt's not really possible to get here.\nHow am I supposed to help you?!\nI have nooo Idea what you did.");
		}
	}

	protected void printLeftAligned(StylizedTextPart rowPart, float x, float y) throws IOException {
		super.printString(rowPart.getText(), x, y, PagerUtils.selectFont(this, rowPart), getFontSize(), getColor());
	}

	protected void printLeftAligned(StylizedTextPart rowPart, float x, float y, boolean underlined) throws IOException {
		super.printString(rowPart.getText(), x, y, PagerUtils.selectFont(this, rowPart), getFontSize(), getColor());
		super.underline(x, y + super.getUnderLineDifference(), x + PagerUtils.formatWidth(this, rowPart),
				y + super.getUnderLineDifference());
	}

	private void printTakeNumber(Integer lineNr) throws IOException {
		if (controller.options.printTakeNumber()) {
			if (currentDual != null && currentDual == Dual.SECOND) {
				float nrWidth = getFont().getStringWidth(lineNr.toString()) / 1000 * getFontSize();
				printString(lineNr.toString(), getMarginLeft() + (getPageWidth() / 2) + 30 - nrWidth, yPos, getFont(),
						getFontSize(), getColor());
			} else {
				float nrWidth = getFont().getStringWidth(lineNr.toString()) / 1000 * getFontSize();
				printString(lineNr.toString(), getMarginLeft() - nrWidth - 10, yPos, getFont(), getFontSize(),
						getColor());
			}
		}
	}

}
