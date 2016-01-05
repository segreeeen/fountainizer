package at.hsol.fountainizer.parser;

import java.util.LinkedList;
import java.util.function.Function;

import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserLines;
import at.hsol.fountainizer.parser.line.Formatter;
import at.hsol.fountainizer.parser.line.SimpleLine;
import at.hsol.fountainizer.parser.line.TitlePage;
import at.hsol.fountainizer.parser.line.TitlePageLine;
import at.hsol.fountainizer.parser.types.ParserConstants;
import at.hsol.fountainizer.parser.types.TitleLineType;

/**
 * @author Felix Batusic
 */
class TitleParser {
    /*
     * Parses the titlepage
     */
    TitlePage parse(ParserLines outputLines, LinkedList<Function<ParserLine, ParserLine>> titleHandlers) {
	TitlePage titlePage = new TitlePage();
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    SimpleLine l = outputLines.get(i);
	    if (titleHandlers != null && !titleHandlers.isEmpty()) {
		l = callTitleHandlers(l, titleHandlers);
	    }
	    TitlePageLine tpl;
	    if (!l.emptyText()) {
		l.setText(l.getText());
		if (isTitle(l) == TitleLineType.CENTERED) {
		    // find text that is going to be centered on the page
		    l.setLineType(TitleLineType.CENTERED);
		    tpl = new TitlePageLine(TitleLineType.CENTERED);
		    String fText = Formatter.format(l.getText(), TitleLineType.CENTERED);
		    if (fText != null) {
			SimpleLine newLine = new SimpleLine(fText, l.getLineNr());
			newLine.setLineType(TitleLineType.CENTERED);
			tpl.addLine(newLine);
		    }
		    i = setFollowingTitles(i, l, outputLines, tpl);
		    outputLines.remove(l);
		    titlePage.addLine(tpl);

		} else if (isTitle(l) == TitleLineType.LEFT) {
		    // find text that is going to be centered on the page
		    l.setLineType(TitleLineType.LEFT);
		    tpl = new TitlePageLine(TitleLineType.LEFT);
		    String fText = Formatter.format(l.getText(), TitleLineType.LEFT);
		    if (fText != null) {
			SimpleLine newLine = new SimpleLine(fText, l.getLineNr());
			newLine.setLineType(TitleLineType.LEFT);
			tpl.addLine(newLine);
		    }

		    i = setFollowingTitles(i, l, outputLines, tpl);
		    outputLines.remove(l);
		    titlePage.addLine(tpl);
		}
	    }
	}
	return titlePage;
    }

    TitlePage parse(ParserLines outputLines) {
	return parse(outputLines, null);
    }

    private int setFollowingTitles(int i, SimpleLine l, ParserLines outputLines, TitlePageLine tpl) {
	SimpleLine iterator = outputLines.getNext(l);
	if (iterator != null) {
	    while (outputLines.hasNext(iterator)) {
		if (iterator.emptyText() || isTitle(iterator) != null) {
		    return iterator.getLineNr() - 2;
		}
		iterator.setLineType(tpl.getLineType());
		tpl.addLine(iterator);
		outputLines.remove(iterator);
		String fText = Formatter.format(iterator.getText(), iterator.getLineType());
		if (fText != null) {
		    iterator.setText(fText);
		}
		iterator = outputLines.getNext(iterator);
	    }
	    return iterator.getLineNr();
	}
	return i;
    }

    private TitleLineType isTitle(ParserLine l) {
	if (!l.emptyText()) {
	    if (l.getText().matches(ParserConstants.TP_CENTERED)) {
		return TitleLineType.CENTERED;
	    } else if (l.getText().matches(ParserConstants.TP_LEFT)) {
		return TitleLineType.LEFT;
	    }
	}
	return null;
    }

    private SimpleLine callTitleHandlers(SimpleLine l, LinkedList<Function<ParserLine, ParserLine>> titleHandlers) {
	if (!titleHandlers.isEmpty()) {
	    for (Function<ParserLine, ParserLine> c : titleHandlers) {
		c.apply(l);
	    }
	    return l;
	} else
	    throw new IllegalArgumentException("handlers can't be empty or null");
    }

}
