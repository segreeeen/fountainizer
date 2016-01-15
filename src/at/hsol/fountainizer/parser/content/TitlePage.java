package at.hsol.fountainizer.parser.content;

import java.util.HashMap;
import java.util.LinkedList;

import at.hsol.fountainizer.parser.interfaces.ParserType;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.parser.types.TitlePageType;
import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

/**
 * This is the TitlePage, it is used as a substitute for all the title Lines and
 * in the end is printed as a Title Page. It consists of a Map of TitleLines
 * that are found in the first run of parsing. It does not contain any actual
 * text.
 * 
 * @author Felix Batusic
 * 
 */
public class TitlePage {
    private final HashMap<TitlePageType, TitlePageLine> titlePageLines;
    private ParserType type = LineType.TITLEPAGE;

    public TitlePage() {
	this.titlePageLines = new HashMap<TitlePageType, TitlePageLine>();
    }

    public void addLine(TitlePageType t, TitlePageLine l) {
	titlePageLines.put(t, l);
    }

    public LinkedList<Paragraph> getParagraphForPDF(TitlePageType type) {
	LinkedList<Paragraph> paragraphs = new LinkedList<>();
	if (titlePageLines.containsKey(type)) {
	    titlePageLines.get(type);
	    for (Paragraph p : titlePageLines.get(type).getParagraphForPDF()) {
		paragraphs.add(p);
	    }
	    return paragraphs;
	} else {
	    return null;
	}
    }

    public TitlePageLine getLine(TitlePageType t) {
	return titlePageLines.get(t);
    }

    public boolean contains(TitlePageType t) {
	return titlePageLines.containsKey(t);
    }

    public ParserType getLineType() {
	return type;
    }

    public boolean emptyText() {
	return titlePageLines.isEmpty();
    }

    public String getText() {
	return type.toString();
    }

    public boolean isEmpty() {
	return titlePageLines.isEmpty();
    }

}
