package at.hsol.fountainizer.printer.pdf.content;

import at.hsol.fountainizer.core.api.parser.LineType;
import at.hsol.fountainizer.core.api.parser.StylizedText;
import at.hsol.fountainizer.printer.pdf.pager.AbstractPager;
import at.hsol.fountainizer.printer.pdf.pager.PagerUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lukas Theis
 */
public class Paragraph implements Margin {

	// private String text;
	private StylizedText stylizedText;

	public StylizedText getRichString() {
		return stylizedText;
	}

	private AbstractPager<?> pager;
	private LineType linetype;
	private List<StylizedText> lines;

	private float marginTop;
	private float marginLeft;
	private float marginRight;
	private float marginBottom = 10.0f;

	private boolean uppercase = false;
	private boolean centered = false;
	private boolean underlined = false;
	private Integer lineTypeNumber;
	private boolean dualDialogue;

	public Paragraph(StylizedText fountainEmphasizedText) {
		this.stylizedText = fountainEmphasizedText;
	}

	public Paragraph(LineType type) {
		this.linetype = type;
	}

	public void initForPager(AbstractPager<?> abstractPager) throws IOException {
		this.pager = abstractPager;
		this.lines = initLines();
	}

	public List<StylizedText> getLines() {
		return lines;
	}

	private List<StylizedText> initLines() throws IOException {
		if (pager == null) {
			return null;
		}

		List<StylizedText> lines = new LinkedList<>();
		if (linetype == LineType.TRANSITION) {
			lines.add(stylizedText);
			return lines;
		}
		String text = this.stylizedText.toString();

		if (isUppercase()) {
			text = text.toUpperCase();
			stylizedText.convertToUpperCase();
		}

		int start = 0;

		while (start < text.length()) {
			int end = start;
			while (PagerUtils.stringWidth(pager, stylizedText.substring(start, end)) < getActualPageWidth() && end < text.length()) {
				end++;
			}
			int lastSpace = stylizedText.substring(start, end).toString().lastIndexOf(" ");
			if (end < text.length() && lastSpace > 0) {
				end = start + lastSpace + 1;
			}

			lines.add(stylizedText.substring(start, end));
			start = end;
		}

		return lines;
	}

	public float getActualPageWidth() {
		if (pager == null) {
			return 0.0f;
		}
		return pager.getPageWidth() - getMarginLeft() - getMarginRight();
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

	@Override
	public boolean isUppercase() {
		return uppercase;
	}

	public void setUppercase(boolean uppercase) {
		this.uppercase = uppercase;
	}

	@Override
	public boolean isCentered() {
		return centered;
	}

	public void setCentered(boolean centered) {
		this.centered = centered;
	}

	@Override
	public boolean isUnderlined() {
		return underlined;
	}

	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	public LineType getLinetype() {
		return linetype;
	}

	public void setLinetype(LineType linetype) {
		this.linetype = linetype;
	}

	public static Paragraph getEmptyParagraph() {
		return new Paragraph(LineType.EMPTY);
	}

	public void setLineTypeNumber(int lineTypeNumber) {
		this.lineTypeNumber = lineTypeNumber;
	}

	public Integer getLineTypeNumber() {
		return lineTypeNumber;
	}

	public boolean isDualDialogue() {
		return dualDialogue;
	}

	public void setDualDialogue(boolean dualDialogue) {
		this.dualDialogue = dualDialogue;
	}

}