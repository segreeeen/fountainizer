/*  Copyright (C) 2015  Felix Batusic
    You should have received a copy of the GNU General Public License along
    with this program	*/

package at.hsol.fountainizer.parser;

import java.util.LinkedList;
import java.util.function.Function;

import at.hsol.fountainizer.parser.data.Characters;
import at.hsol.fountainizer.parser.data.Statistic;
import at.hsol.fountainizer.parser.interfaces.ParserLine;
import at.hsol.fountainizer.parser.interfaces.ParserList;
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
    private Statistic stats; 
    private TypeHelper typeHelper;

    
    public Parser() {
	this.stats = new Statistic();
	this.typeHelper = new TypeHelper(stats);
    }

    public ParserList parse(ParserList outputLines, LinkedList<Function<SimpleLine, SimpleLine>> parserHandlers, LinkedList<Function<ParserLine, ParserLine>> titleHandlers) {
	TitlePage tp;
	TitleParser tParser = new TitleParser();
	if (titleHandlers != null && !titleHandlers.isEmpty()) {
	    tp = tParser.parse(outputLines, titleHandlers);
	} else {
	    tp = tParser.parse(outputLines);
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

	for (SimpleLine l: outputLines) {
	    if (l.getLineType() == LineType.CHARACTER) {
		l.setText(outputLines.getCharacters().lookup(l.getText()));
	    }
	}
	return outputLines;
    }

    public ParserList parse(ParserList outputLines) {
	return parse(outputLines, null, null);
    }
    
    public Statistic getStats() {
        return stats;
    }

    private void setAttributes(SimpleLine l, ParserList outputLines) {
	LineType type = typeHelper.getType(l, outputLines);
	l.setLineType(type);
	String fText = Formatter.format(l.getText(), type);
	if (fText != null) {
	    l.setText(fText);
	}
	String text = l.getText();
	if (type == LineType.CHARACTER && text.matches(ParserConstants.L_DUAL_DIALOGUE)) {
	    setDualDialogue(l, outputLines);
	}

	if (type == LineType.CHARACTER) {
		outputLines.getCharacters().add(text);
	}
    }

    private void setDualDialogue(SimpleLine l, ParserList outputLines) {
	l.setDualDialogue(true);
	l.setText(l.getText().replaceAll("\\^", ""));

	// set dualdialogue backwards
	SimpleLine bIterator = outputLines.getPrev(l);
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
	SimpleLine fIterator = outputLines.getNext(l);
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

    private SimpleLine callParserHandlers(SimpleLine line, LinkedList<Function<SimpleLine, SimpleLine>> handler) {
	if (!handler.isEmpty()) {
	    for (Function<SimpleLine, SimpleLine> c : handler) {
		c.apply(line);
	    }
	    return line;
	} else
	    throw new IllegalArgumentException("handlers can't be empty or null");
    }

}
