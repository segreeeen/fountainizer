/*  Copyright (C) 2015  Felix Batusic
    You should have received a copy of the GNU General Public License along
    with this program	*/

package at.hsol.fountainizer.parser;

import java.util.LinkedList;
import java.util.function.Function;

import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserLines;
import at.hsol.fountainizer.parser.line.Formatter;
import at.hsol.fountainizer.parser.line.SimpleLine;
import at.hsol.fountainizer.parser.line.TitlePage;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.parser.types.ParserConstants;
import at.hsol.fountainizer.parser.types.TypeHelper;


/**
 * @author Felix Batusic
 */
public class Parser {

    public static ParserLines parse(ParserLines outputLines, LinkedList<Function<SimpleLine, SimpleLine>> parserHandlers, LinkedList<Function<ParserLine, ParserLine>> titleHandlers) {
	TitlePage tp;
	if (titleHandlers != null && !titleHandlers.isEmpty()){
	    tp = TitleParser.parse(outputLines, titleHandlers);
	} else {
	    tp = TitleParser.parse(outputLines);
	}
	outputLines.setTitlepage(tp);
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    SimpleLine l = (SimpleLine) outputLines.get(i);
	    if (l.getText() == null) {
		l.setLineType(LineType.EMPTY);
		continue;
	    }
	    setAttributes(l, outputLines);
	}

	if (parserHandlers != null && !parserHandlers.isEmpty()) {
	    for (int i = 0; i < outputLines.getLineCount(); i++) {
		SimpleLine l = (SimpleLine) outputLines.get(i);
		l = callParserHandlers(l, parserHandlers);
	    }
	}
	return outputLines;
    }

    public static ParserLines parse(ParserLines outputLines) {
	return parse(outputLines, null, null);
    }

    private static void setAttributes(SimpleLine l, ParserLines outputLines) {
	LineType type = TypeHelper.getType(l, outputLines);
	l.setLineType(type);
	String fText = Formatter.format(l.getText(), type);
	if (fText != null) {
	    l.setText(fText);
	}
	String text = l.getText();
	if (type == LineType.CHARACTER && text.matches(ParserConstants.L_DUAL_DIALOGUE)) {
	    setDualDialogue(l, outputLines);
	}
    }

    private static void setDualDialogue(SimpleLine l, ParserLines outputLines) {
	l.setDualDialogue(true);
	l.setText(l.getText().replaceAll("\\^", ""));

	// set dualdialogue backwards
	ParserLine bIterator = outputLines.getPrev(l);
	if (bIterator != null) {
	    while (bIterator.getLineType() != LineType.CHARACTER) {
		if (l.getLineNr() - bIterator.getLineNr() > 6) {
		    break;
		}
		bIterator.setDualDialogue(true);
		bIterator = outputLines.getPrev(bIterator);
	    }
	    bIterator.setDualDialogue(true);
	}

	// set dualdialogue forward
	ParserLine fIterator = outputLines.getNext(l);
	if (fIterator != null) {
	    while (fIterator != null && fIterator.getLineType() != LineType.EMPTY) {
		if (fIterator.emptyText()) {
		    break;
		}

		if (fIterator.getLineNr() - l.getLineNr() > 6) {
		    break;
		}
		fIterator.setDualDialogue(true);
		fIterator = outputLines.getNext(fIterator);
	    }
	    fIterator.setDualDialogue(true);
	}
    }

    

    private static SimpleLine callParserHandlers(SimpleLine line, LinkedList<Function<SimpleLine, SimpleLine>> handler) {
	if (!handler.isEmpty()) {
	    for (Function<SimpleLine, SimpleLine> c : handler) {
		c.apply(line);
	    }
	    return line;
	} else
	    throw new IllegalArgumentException("handlers can't be empty or null");
    }

}
