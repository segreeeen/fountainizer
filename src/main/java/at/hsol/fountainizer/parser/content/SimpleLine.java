package at.hsol.fountainizer.parser.content;

import java.util.LinkedList;

import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.pdfbox.paragraph.RichString;
import at.hsol.fountainizer.parser.interfaces.Line;
import at.hsol.fountainizer.parser.interfaces.MarginType;

/**
 * @author Felix Batusic
 */
public class SimpleLine implements Line {
	private String text;
	private int lineNr;
	private MarginType type;
	private boolean dualDialogue;
	private int lineTypeNumber;
	private SimpleLine prev = null;
	private SimpleLine next = null;

	public SimpleLine(String text, int lineNr) {
		this.text = text;
		this.lineNr = lineNr;
		this.dualDialogue = false;
	}

	@Override
	public MarginType getLineType() {
		return type;
	}

	public void setLineType(MarginType type) {
		this.type = type;
	}

	public boolean isDualDialogue() {
		return dualDialogue;
	}

	public void setDualDialogue(boolean dualDialogue) {
		this.dualDialogue = dualDialogue;
	}

	public int getLineTypeNumber() {
		return lineTypeNumber;
	}

	public void setLineTypeNumber(int takeNumber) {
		this.lineTypeNumber = takeNumber;
	}

	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLineNr() {
		return lineNr;
	}

	public void incLineNr() {
		this.lineNr++;
	}

	public void decLineNr() {
		this.lineNr--;
	}

	@Override
	public boolean emptyText() {
		return this.text == null;
	}

	public SimpleLine getPrev() {
		return prev;
	}

	public void setPrev(SimpleLine prev) {
		this.prev = prev;
	}

	public SimpleLine getNext() {
		return next;
	}

	public void setNext(SimpleLine next) {
		this.next = next;
	}

	public boolean hasNext() {
		return next != null;
	}

	public boolean hasPrev() {
		return prev != null;
	}

	@Override
	public LinkedList<Paragraph> getParagraphForPDF() {
		if (getText() != null) {
			RichString s = new RichString(getText(), null);
			Paragraph p = new Paragraph(s);
			p.setLinetype(type);
			p.setUppercase(type.isUppercase());
			p.setUnderlined(type.isUnderlined());
			p.setCentered(type.isCentered());
			p.setMargin(type.getMarginTop(), type.getMarginLeft(), type.getMarginRight(), type.getMarginBottom());
			LinkedList<Paragraph> paragraphs = new LinkedList<>();
			p.setLineTypeNumber(lineTypeNumber);
			p.setDualDialogue(dualDialogue);
			paragraphs.add(p);
			return paragraphs;
		} else {
			LinkedList<Paragraph> paragraphs = new LinkedList<>();
			paragraphs.add(Paragraph.getEmptyParagraph());
			return paragraphs;
		}
	}

}