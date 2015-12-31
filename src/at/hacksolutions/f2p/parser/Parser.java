package at.hacksolutions.f2p.parser;

import at.hacksolutions.f2p.parser.line.SimpleLine;
import at.hacksolutions.f2p.parser.line.ParserLine;
import at.hacksolutions.f2p.parser.line.ParserLines;
import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;
import at.hacksolutions.f2p.parser.types.TitleLineType;
import at.hacksolutions.f2p.parser.types.TypeHelper;

public class Parser {

    public static ParserLines parse(ParserLines outputLines) {
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    SimpleLine l = outputLines.get(i);
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
	setDualDialogue(l, outputLines);
    }

    private static void setDualDialogue(SimpleLine l, ParserLines outputLines) {
	String text = l.getText();
	LineType type = TypeHelper.getType(l, outputLines);
	if (type == LineType.CHARACTER
		&& text.matches(ParserConstants.L_DUAL_DIALOGUE)) {
	    ParserLine iterator = outputLines.getPrev(l);
	    if (iterator != null) {
		while (iterator.getLineType() != LineType.CHARACTER) {
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

    private void parseTitles(ParserLines lines) {
	for (ParserLine l : lines) {
	    if (!l.emptyText()) {
		if (l.getText().matches(ParserConstants.TP_CENTERED)) {
		    l.setLineType(TitleLineType.CENTERED);
		} else if (l.getText().matches(ParserConstants.TP_LEFT)) {
		    l.setLineType(TitleLineType.LEFT);
		}
	    }
	}
    }

    private boolean isTitle(ParserLine l) {
	if (!l.emptyText()) {
	    if (l.getText().matches(ParserConstants.TP_CENTERED)) {
		return true;
	    } else if (l.getText().matches(ParserConstants.TP_LEFT)) {
		return true;
	    }
	}
	return false;
    }

}
