package at.hsol.fountainizer.parser;

import java.util.LinkedList;

import at.hsol.fountainizer.parser.content.Formatter;
import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.content.TitlePage;
import at.hsol.fountainizer.parser.content.TitlePageLine;
import at.hsol.fountainizer.parser.interfaces.ParserList;
import at.hsol.fountainizer.parser.types.ParserConstants;
import at.hsol.fountainizer.parser.types.TitlePageType;

/**
 * @author Felix Batusic
 */
class TitleParser {
    private TitlePage titlePage = new TitlePage();

    public TitlePage parse(ParserList outputLines) {
	if (getTitle(outputLines.get(0)) == null) {
	    return null;
	}

	LinkedList<SimpleLine> titleLines = new LinkedList<>();
	SimpleLine s = outputLines.get(0);

	while (s.hasNext()) {
	    if (!s.emptyText()) {
		outputLines.remove(s);
		titleLines.add(s);
	    } else {
		break;
	    }
	    s = s.getNext();
	}

	parseTPL(titleLines);

	if (!titlePage.isEmpty()) {
	    return titlePage;
	} else {
	    return null;
	}
    }

    private void parseTPL(LinkedList<SimpleLine> titleLines) {
	TitlePageLine tpl = new TitlePageLine();
	for (SimpleLine l : titleLines) {
	    TitlePageType t = getTitle(l);
	    if (t != null) {
		String s = Formatter.format(l.getText(), t);
		l.setText(s);
		if (titlePage.contains(t)) {
		    tpl = titlePage.getLine(t);
		    if (l.getText()!= null) {
			tpl.add(l);
		    }
		} else {
		    tpl = new TitlePageLine(t);
		    if (l.getText()!= null) {
			tpl.add(l);
		    }
		    titlePage.addLine(t, tpl);
		}
	    } else {
		tpl.add(l);
	    }
	}
    }

    private TitlePageType getTitle(SimpleLine l) {
	if (!l.emptyText()) {
	    if (l.getText().matches(ParserConstants.TP_CENTERED)) {
		return TitlePageType.CENTERED;
	    } else if (l.getText().matches(ParserConstants.TP_LEFT)) {
		return TitlePageType.LEFT;
	    } else if (l.getText().matches(ParserConstants.TP_TITLE)) {
		return TitlePageType.TITLE;
	    } else if (l.getText().matches(ParserConstants.TP_RIGHT)) {
		return TitlePageType.RIGHT;
	    } else {
		return null;
	    }
	}
	return null;
    }

}
