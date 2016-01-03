package at.hacksolutions.f2p.parser;

import java.util.LinkedList;
import java.util.function.Function;

import at.hacksolutions.f2p.parser.interfaces.ParserLine;
import at.hacksolutions.f2p.parser.interfaces.ParserLines;
import at.hacksolutions.f2p.parser.line.Formatter;
import at.hacksolutions.f2p.parser.line.SimpleLine;
import at.hacksolutions.f2p.parser.line.TitlePage;
import at.hacksolutions.f2p.parser.line.TitlePageLine;
import at.hacksolutions.f2p.parser.types.ParserConstants;
import at.hacksolutions.f2p.parser.types.TitleLineType;

/**
 * @author Felix Batusic
 */
class TitleParser {
    /*
     * Parses the titlepage
     */
    static TitlePage parse(ParserLines outputLines, LinkedList<Function<ParserLine, ParserLine>> titleHandlers) {
	TitlePage titlePage = new TitlePage();
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    ParserLine l = outputLines.get(i);
	    if (titleHandlers != null && !titleHandlers.isEmpty()) {
		l = callTitleHandlers(l, titleHandlers);
	    }
	    TitlePageLine tpl;
	    if (!l.emptyText()) {
		((SimpleLine) l).setText(l.getText());
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

    static TitlePage parse(ParserLines outputLines) {
	return parse(outputLines, null);
    }

    private static int setFollowingTitles(int i, ParserLine l, ParserLines outputLines, TitlePageLine tpl) {
	SimpleLine iterator = (SimpleLine) outputLines.getNext(l);
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
		iterator = (SimpleLine) outputLines.getNext(iterator);
	    }
	    return iterator.getLineNr();
	}
	return i;
    }

    private static TitleLineType isTitle(ParserLine l) {
	if (!l.emptyText()) {
	    if (l.getText().matches(ParserConstants.TP_CENTERED)) {
		return TitleLineType.CENTERED;
	    } else if (l.getText().matches(ParserConstants.TP_LEFT)) {
		return TitleLineType.LEFT;
	    }
	}
	return null;
    }

    private static ParserLine callTitleHandlers(ParserLine l, LinkedList<Function<ParserLine, ParserLine>> titleHandlers) {
	if (!titleHandlers.isEmpty()) {
	    for (Function<ParserLine, ParserLine> c : titleHandlers) {
		c.apply(l);
	    }
	    return l;
	} else
	    throw new IllegalArgumentException("handlers can't be empty or null");
    }

}
