package at.hacksolutions.f2p.parser;

import at.hacksolutions.f2p.parser.line.Line;
import at.hacksolutions.f2p.parser.line.Lines;
import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserConstants;

public class Parser {

    public static Lines parse(Lines outputLines) {
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    Line l = outputLines.get(i);
	    if (l.getText() == null) {
		l.setLineType(LineType.EMPTY);
		continue;
	    }
	    setAttributes(l, outputLines);
	}
	return outputLines;
    }

    private static void setAttributes(Line l, Lines outputLines) {
	LineType type = LineType.getType(l, outputLines);
	l.setLineType(type);
	setDualDialogue(l, outputLines);
    }

    private static void setDualDialogue(Line l, Lines outputLines) {
	String text = l.getText();
	LineType type = LineType.getType(l, outputLines);
	if (type == LineType.CHARACTER && text.matches(ParserConstants.L_DUAL_DIALOGUE)) {
		Line iterator = outputLines.getPrev(l);
		while (iterator.getLineType() != LineType.CHARACTER) {
		    iterator = outputLines.getPrev(iterator);
		    System.out.println(iterator.getLineNr());
		    if (l.getLineNr() - iterator.getLineNr() > 3) {
			break;
		    }
		}
		iterator.setDualDialogue(true);
		l.setDualDialogue(true);
	}
    }

}
