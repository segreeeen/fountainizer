/*  Copyright (C) 2015  Felix Batusic
    You should have received a copy of the GNU General Public License along
    with this program	*/

package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.parser.content.ParserContent;
import at.hsol.fountainizer.parser.content.Formatter;
import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.content.TitlePage;
import at.hsol.fountainizer.parser.interfaces.ParserList;
import at.hsol.fountainizer.parser.types.LineType;
import at.hsol.fountainizer.parser.types.ParserConstants;
import at.hsol.fountainizer.parser.types.Statistic;
import at.hsol.fountainizer.parser.types.TypeHelper;

/**
 * @author Felix Batusic
 */
public class Parser {
    private Statistic stats;
    private TypeHelper typeHelper;
    private ParserList outputLines;

    public Parser(ParserContent outputLines) {
	this.stats = new Statistic(outputLines.getCharacters());
	this.typeHelper = new TypeHelper(stats, outputLines);
	this.outputLines = outputLines;
    }

    public ParserList parse() {
	TitleParser tParser = new TitleParser();
	TitlePage tp = tParser.parse(outputLines);
	outputLines.setTitlepage(tp);
	for (int i = 0; i < outputLines.getLineCount(); i++) {
	    SimpleLine l = (SimpleLine) outputLines.get(i);
	    if (l.getText() == null) {
		l.setLineType(LineType.EMPTY);
		continue;
	    }
	    setAttributes(l, outputLines);
	}

	for (SimpleLine l : outputLines) {
	    if (l.getLineType() == LineType.CHARACTER) {
		l.setText(outputLines.getCharacters().lookup(l.getText()));
	    }
	}
	return outputLines;
    }

    public Statistic getStats() {
	return stats;
    }

    private void setAttributes(SimpleLine l, ParserList outputLines) {
	LineType type = typeHelper.getType(l);
	l.setLineType(type);
	String fText = Formatter.format(l.getText(), type);
	if (fText != null) {
	    l.setText(fText);
	}
	String text = l.getText();
	if (type == LineType.CHARACTER && text.matches(ParserConstants.L_DUAL_DIALOGUE)) {
	    setDualDialogue(l, outputLines);
	}

//	if (type == LineType.CHARACTER) {
//	    outputLines.getCharacters().add(text);
//	    outputLines.getCharacters().incCharCount(text);
//	}
    }

    private void setDualDialogue(SimpleLine l, ParserList outputLines) {
	l.setDualDialogue(true);
	l.setText(l.getText().replaceAll("\\^", ""));

	// set dualdialogue backwards
	SimpleLine bIterator = l.getPrev();
	if (bIterator != null) {
	    while (bIterator.getPrev() != null && bIterator.getLineType() != LineType.CHARACTER) {
		if (l.getLineNr() - bIterator.getLineNr() > 6) {
		    break;
		}
		bIterator.setDualDialogue(true);
		bIterator = bIterator.getPrev();
	    }
	    bIterator.setDualDialogue(true);
	}

	// set dualdialogue forward
	SimpleLine fIterator = l.getNext();
	if (fIterator != null) {
	    while (fIterator.getNext() != null && fIterator.getLineType() != LineType.EMPTY) {
		if (fIterator.emptyText()) {
		    break;
		}

		if (fIterator.getLineNr() - l.getLineNr() > 6) {
		    break;
		}
		fIterator.setDualDialogue(true);
		fIterator = fIterator.getNext();
	    }
	    fIterator.setDualDialogue(true);
	}
    }
}
