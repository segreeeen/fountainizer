package at.hsol.fountainizer.printer.pdf.pager;

import at.hsol.fountainizer.core.api.parser.CharacterInfo;

import java.io.IOException;
import java.util.List;

public class CharacterPager extends AbstractPager<List<? extends CharacterInfo>> {

	CharacterPager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
		super(controller);
		super.type = type;
	}

	@Override
	public void printContent(List<? extends CharacterInfo> characterDescriptionList) throws IOException {
		if (characterDescriptionList == null || characterDescriptionList.isEmpty()) return;

		float xLines = 400;
		printHeader();
		super.nextLine();
		super.fontSize = 14;
		super.printString("Name", super.xPos, super.yPos, getItalicFont(), getFontSize(),
				PagerController.STANDARD_TEXT_COLOR);
		super.printString("Takes", super.xPos + xLines, yPos, getItalicFont(), getFontSize(),
				PagerController.STANDARD_TEXT_COLOR);
		nextLine(5f);
		super.fontSize = null;
		for (CharacterInfo c : characterDescriptionList) {
			super.printString(c.getName().toUpperCase(), super.xPos, super.yPos, getFont(), getFontSize(),
					PagerController.STANDARD_TEXT_COLOR);
			super.printString(Integer.toString(c.getTakes()), super.xPos + xLines, super.yPos, getFont(), getFontSize(),
					PagerController.STANDARD_TEXT_COLOR);
			nextLine(15f);
			if (super.yExceeded()) {
				super.nextPage();
			}
		}

	}

	private void printHeader() throws IOException {
		super.fontSize = 18;
		super.printString("CharacterInfo Index", super.xPos, super.yPos, getFont(), getFontSize(),
				PagerController.STANDARD_TEXT_COLOR);
		super.nextLine(30f);
		super.fontSize = null;
	}

}
