/*  Copyright (C) 2015  Felix Batusic
    You should have received a copy of the GNU General Public License along
    with this program	*/

package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.parser.content.ParserContent;
import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.content.TitlePage;
import at.hsol.fountainizer.parser.interfaces.Content;
import at.hsol.fountainizer.parser.meta.Statistic;
import at.hsol.fountainizer.parser.types.LineType;

/**
 * @author Felix Batusic
 */
public class Parser {
    private Statistic stats;
    private TypeHelper typeHelper;
    private Content outputLines;

    public Parser(ParserContent outputLines) {
	this.stats = new Statistic(outputLines.getCharacters());
	this.typeHelper = new TypeHelper(outputLines);
	this.outputLines = outputLines;
    }

    public Content parse() {
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

    private void setAttributes(SimpleLine l, Content outputLines) {
	// get helper instance
	LineType type = typeHelper.getType(l);
	
	// get linetype
	l.setLineType(type);
	
	//format text if it conforms to a linetype
	String fText = Formatter.format(l.getText(), type);
	if (fText != null) {
	    l.setText(fText);
	}
	
	//count the linetype and set it's number
	stats.countLine(l);
	l.setLineTypeNumber(stats.getCharacterLines());
	
	//set dual dialog flags
	String text = l.getText();
	if (type == LineType.CHARACTER && text.matches(ParserConstants.L_DUAL_DIALOGUE)) {
	    setDualDialogue(l, outputLines);
	}

    }

    private void setDualDialogue(SimpleLine l, Content outputLines) {
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
