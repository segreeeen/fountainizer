package at.hsol.fountainizer.parser.content;

import java.util.LinkedList;

import at.hsol.fountainizer.parser.types.TitlePageType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;
import at.hsol.fountainizer.parser.interfaces.Line;
import at.hsol.fountainizer.parser.interfaces.MarginType;

/**
 * @author Felix Batusic
 */
public class TitlePageLine implements Line {
	private TitlePageType type;
	private final LinkedList<Line> lines;

	public TitlePageLine(TitlePageType t) {
		this.lines = new LinkedList<>();
		this.type = t;
	}

	public TitlePageLine() {
		this(null);
	}

	public LinkedList<Line> getLines() {
		return lines;
	}

	@Override
	public LinkedList<Paragraph> getParagraphForPDF() {
		LinkedList<Paragraph> p = new LinkedList<>();
		for (Line t : lines) {
			p.addAll(t.getParagraphForPDF());
		}
		return p;
	}

	public void add(SimpleLine l) {
		l.setLineType(type);
		lines.add(l);
	}

	@Override
	public MarginType getLineType() {
		return type;
	}

	@Override
	public boolean emptyText() {
		return getLines().isEmpty();
	}

	@Override
	public String getText() {
		return type.toString();
	}

	public void setType(TitlePageType t) {
		this.type = t;
	}

}
