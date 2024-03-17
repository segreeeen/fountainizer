package at.hsol.fountainizer.core.parser;

import at.hsol.fountainizer.core.parser.api.HeadingType;
import at.hsol.fountainizer.core.parser.api.Line;
import at.hsol.fountainizer.core.parser.api.LineType;

/**
 * @author Felix Batusic
 */
public class ParserLine implements Line {
	private String text;
	private int lineNr;
	private LineType type;
	private boolean dualDialogue;
	private int lineTypeNumber;
	private ParserLine prev = null;
	private ParserLine next = null;
	private HeadingType headingType;

	ParserLine(String text, int lineNr) {
		this.text = text;
		this.lineNr = lineNr;
		this.dualDialogue = false;
	}


	@Override
	public boolean isDualDialogue() {
		return dualDialogue;
	}

	@Override
	public int getLineTypeNumber() {
		return lineTypeNumber;
	}


	@Override
	public String getText() {
		return text;
	}

	@Override
	public LineType getLineType() {
		return this.type;
	}

	@Override
	public HeadingType getHeadingType() {
		return this.headingType;
	}

	@Override
	public int getLineNr() {
		return lineNr;
	}

	@Override
	public boolean emptyText() {
		return this.text == null || this.text.isEmpty();
	}

	@Override
	public ParserLine getPrev() {
		return prev;
	}

	@Override
	public ParserLine getNext() {
		return next;
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public boolean hasPrev() {
		return prev != null;
	}

	@Override
	public FountainEmphasizedText getRichString() {
		return new FountainEmphasizedText(getText(), null);
	}

	void setType(LineType type) {
		this.type = type;
	}

	void setNext(ParserLine next) {
		this.next = next;
	}

	void setPrev(ParserLine prev) {
		this.prev = prev;
	}

	void incLineNr() {
		this.lineNr++;
	}

	void decLineNr() {
		this.lineNr--;
	}

	void setText(String text) {
		this.text = text;
	}

	void setLineTypeNumber(int takeNumber) {
		this.lineTypeNumber = takeNumber;
	}

	void setDualDialogue(boolean dualDialogue) {
		this.dualDialogue = dualDialogue;
	}

	void setLineType(LineType type) {
		this.type = type;
	}
}