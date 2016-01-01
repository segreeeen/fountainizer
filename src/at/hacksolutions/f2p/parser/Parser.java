package at.hacksolutions.f2p.parser;

import at.hacksolutions.f2p.parser.line.SimpleLine;
import at.hacksolutions.f2p.parser.line.TitlePage;
import at.hacksolutions.f2p.parser.line.TitlePageLine;
import at.hacksolutions.f2p.parser.line.ParserLine;
import at.hacksolutions.f2p.parser.line.ParserLines;
import at.hacksolutions.f2p.parser.line.Formatter;
import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;
import at.hacksolutions.f2p.parser.types.TitleLineType;
import at.hacksolutions.f2p.parser.types.TypeHelper;

public class Parser {

    public static ParserLines parse(ParserLines outputLines) {
	TitlePage tp = parseTitles(outputLines);
	outputLines.setTitlepage(tp);
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    SimpleLine l = (SimpleLine) outputLines.get(i);
	    if (l.getText() == null) {
		l.setLineType(LineType.EMPTY);
		continue;
	    }
	    setAttributes(l, outputLines);
	}
	return outputLines;
    }

    private static void setAttributes(SimpleLine l, ParserLines outputLines) {
	LineType type = TypeHelper.getType(l, outputLines);
	l.setLineType(type);
	String fText = Formatter.format(l.getText(), type);
	if (fText != null) {
	    l.setText(fText);
	}
	setDualDialogue(l, outputLines);
    }

    private static void setDualDialogue(SimpleLine l, ParserLines outputLines) {
	String text = l.getText();
	LineType type = TypeHelper.getType(l, outputLines);
	if (type == LineType.CHARACTER
		&& text.matches(ParserConstants.L_DUAL_DIALOGUE)) {
	    ParserLine iterator = outputLines.getPrev(l);
	    if (iterator != null) {
		while (iterator.getLineType() != LineType.CHARACTER ||
			iterator.getLineType() != LineType.DIALOGUE ||
			iterator.getLineType() != LineType.EMPTY) {
		    iterator = outputLines.getPrev(iterator);
		    if (l.getLineNr() - iterator.getLineNr() > 3) {
			break;
		    }
		}

		iterator.setDualDialogue(true);
		l.setDualDialogue(true);
	    }
	}
    }

    private static TitlePage parseTitles(ParserLines outputLines) {
	TitlePage titlePage = new TitlePage();
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    ParserLine l = outputLines.get(i);
	    TitlePageLine tpl;
	    if (!l.emptyText()) {
		((SimpleLine) l).setText(l.getText());
		if (isTitle(l) == TitleLineType.CENTERED) {
		    l.setLineType(TitleLineType.CENTERED);
		    tpl = new TitlePageLine(TitleLineType.CENTERED);
		    String fText = Formatter.format(l.getText(),
			    TitleLineType.CENTERED);
		    if (fText != null) {
			SimpleLine newLine = new SimpleLine(fText,
				l.getLineNr());
			newLine.setLineType(TitleLineType.CENTERED);
			tpl.addLine(newLine);
		    }

		    i = setFollowingTitles(i, l, outputLines, tpl);
		    outputLines.remove(l);
		    titlePage.addLine(tpl);
		} else if (isTitle(l) == TitleLineType.LEFT) {
		    l.setLineType(TitleLineType.LEFT);
		    tpl = new TitlePageLine(TitleLineType.LEFT);
		    String fText = Formatter.format(l.getText(),
			    TitleLineType.LEFT);
		    if (fText != null) {
			SimpleLine newLine = new SimpleLine(fText,
				l.getLineNr());
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

    private static int setFollowingTitles(int i, ParserLine l,
	    ParserLines outputLines, TitlePageLine tpl) {
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

}
