package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.util.List;

import at.hsol.fountainizer.parser.meta.FCharacter;

public class CharacterPager extends AbstractPager<List<FCharacter>> {
    private float HEADING_X = 80;

    CharacterPager(PagerController controller) throws IOException {
	super(controller);
	super.type = PagerController.PagerType.CHARACTER_PAGER;
    }

    @Override
    public void printContent(List<FCharacter> t) throws IOException {
	float xChar = 100;
	float xLines = 400;
	printHeader();
	super.nextLine();
	super.fontSize = 14;
	super.printLeftAligned("Name", xChar, yPos, getItalicFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	super.printLeftAligned("Takes", xLines, yPos, getItalicFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	nextLine(5f);
	super.fontSize = null;
	for (FCharacter c : t) {
	    super.printLeftAligned(c.getName(), xChar, yPos, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	    super.printLeftAligned(Integer.toString(c.getTakes()), xLines, yPos, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	    nextLine(15f);
	}

    }

    private void printHeader() throws IOException {
	super.fontSize = 18;
	super.printLeftAligned("Character Index", HEADING_X, yPos, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	super.nextLine(30f);
	super.fontSize = null;
    }

}
