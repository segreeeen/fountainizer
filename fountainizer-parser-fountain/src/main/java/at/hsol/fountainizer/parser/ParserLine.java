package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.parser.Line;
import at.hsol.fountainizer.core.api.parser.StylizedText;
import at.hsol.fountainizer.core.api.types.HeadingType;
import at.hsol.fountainizer.core.api.types.LineType;
import java.util.function.Function;

public class ParserLine implements Line {
	private String text;

	private int lineNr;

	private LineType type;

	private boolean dualDialogue;

	private int lineTypeNumber;

	private ParserLine prev = null;

	private ParserLine next = null;

	private HeadingType headingType;

	private Function<ParserLine, ParserLine> getPreviousLineFunction;

	private Function<ParserLine, ParserLine> getNextLineFunction;

	ParserLine(String text) {
		this.text = text;
		this.lineNr = this.lineNr;
		this.dualDialogue = false;
	}

	public void setLineNr(int lineNr) {
		this.lineNr = lineNr;
	}

	public boolean isDualDialogue() {
		return this.dualDialogue;
	}

	public int getLineTypeNumber() {
		return this.lineTypeNumber;
	}

	public String getText() {
		return this.text;
	}

	public LineType getLineType() {
		return this.type;
	}

	public HeadingType getHeadingType() {
		return this.headingType;
	}

	public int getLineNr() {
		return this.lineNr;
	}

	public boolean emptyText() {
		return (this.text == null || this.text.isEmpty());
	}

	public ParserLine getPrev() {
		return this.getPreviousLineFunction.apply(this);
	}

	public ParserLine getNext() {
		return this.getNextLineFunction.apply(this);
	}

	public boolean hasNext() {
		return (getNext() != null);
	}

	public boolean hasPrev() {
		return (getPrev() != null);
	}

	public FountainEmphasizedText getRichString() {
		return new FountainEmphasizedText(getText(), null);
	}

	void setType(LineType type) {
		this.type = type;
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

	public void setGetPrevLineFunction(Function<ParserLine, ParserLine> getPreviousLineFunction) {
		this.getPreviousLineFunction = getPreviousLineFunction;
	}

	public void setGetNextLineFunction(Function<ParserLine, ParserLine> getNextLineFunction) {
		this.getNextLineFunction = getNextLineFunction;
	}
}
