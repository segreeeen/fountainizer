/*  Copyright (C) 2015  Felix Batusic
    You should have received a copy of the GNU General Public License along
    with this program	*/

package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.*;
import at.hsol.fountainizer.core.api.types.LineType;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Felix Batusic
 */
class FountainParser implements ParserAPI {
	private final Options options;
	private final TypeHelper typeHelper;
	private ParserContent content;

	FountainParser(ParserContent content, Options options) {
		this.options = options;
		this.content = content;
		this.typeHelper = new TypeHelper(content);
	}

	FountainParser(Options options) {
		this(new ParserContent(options), options);
	}

	void internalizeFountainString(List<String> fountainTextLines) {
		ParserContent parserContent = new ParserContent(this.options);
		for (String line : fountainTextLines) {
			String text = cleanText(line);
			parserContent.addLine(text);
		}
		this.content = parserContent;
	}

	private static String cleanText(String text) {
		text = text.replaceAll("[\\t\\n\\r]", "");
		text = text.replaceAll("\uFFFD", "");
		text = text.replaceAll("\uFEFF", "");
		text = text.replaceAll("\u0084", "");
		text = text.trim();
		return text;
	}

	void parseDocument() {
		parseTitlePage(content);
		for (int i = 0; i < content.getLineCount(); i++) {
			ParserLine l = (ParserLine) content.get(i);
			if (l.getText() == null) {
				l.setLineType(LineType.EMPTY);
				continue;
			}
			setAttributes(l, content);
		}

		for (Line l : content) {
			if (l.getLineType() == LineType.CHARACTER) {
				((ParserLine) l).setText(content.getStats().lookupCharacter(l.getText()));
			}
		}
	}

	private void setAttributes(ParserLine l, Content outputLines) {
		// get helper instance
		LineType type = typeHelper.getType(l);

		// get linetype
		l.setLineType(type);

		// format text if it conforms to a linetype
		String fText = Formatter.format(l.getText(), type);
		if (fText != null) {
			l.setText(fText);
		}

		// count the linetype and set its number
		content.getStats().countLine(l);
		l.setLineTypeNumber(content.getStats().getCharacterLines());

		// set dual dialog flags
		String text = l.getText();
		if (type == LineType.CHARACTER && text.matches(ParserConstants.L_DUAL_DIALOGUE)) {
			setDualDialogue(l, outputLines);
		}

	}

	private void setDualDialogue(ParserLine l, Content outputLines) {
		l.setDualDialogue(true);
		l.setText(l.getText().replaceAll("\\^", ""));

		// set dualdialogue backwards
		ParserLine bIterator = l.getPrev();
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
		ParserLine fIterator = l.getNext();
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


	private void parseTitlePage(ParserContent content) {

		//check if there is a title as first line in script
		if (getTitle(content.get(0)) == null) {
			return;
		}

		List<ParserLine> titleParserLines = new LinkedList<>();
		ParserLine s = (ParserLine) content.get(0);

		while (s.hasNext()) {
			if (!s.emptyText()) {
				content.remove(s);
				titleParserLines.add(s);
			} else {
				break;
			}
			s = s.getNext();
		}

		titleParserLines.forEach(l -> {
			LineType t = getTitle(l);
			l.setType(t);
			l.setText(Formatter.format(l.getText(), t));
			content.addTitlePageLine(t, l);
		});
	}

	private LineType getTitle(Line l) {
		if (!l.emptyText()) {
			if (l.getText().matches(ParserConstants.TP_CENTERED)) {
				return LineType.TITLEPAGE_CENTERED;
			} else if (l.getText().matches(ParserConstants.TP_LEFT)) {
				return LineType.TITLEPAGE_LEFT;
			} else if (l.getText().matches(ParserConstants.TP_TITLE)) {
				return LineType.TITLEPAGE_TITLE;
			} else {
				return null;
			}
		}
		return null;
	}


	public Content getContent() {
		return this.content;
	}

	@Override
	public Content parse(String fountainText) {
		Objects.requireNonNull(fountainText);
		List<String> lines = List.of(fountainText.split("\n"));
		internalizeFountainString(lines);
		parseDocument();
		return this.content;
	}
}
