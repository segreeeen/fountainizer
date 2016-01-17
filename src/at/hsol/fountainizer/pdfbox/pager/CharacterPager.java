package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;
import java.util.List;

import at.hsol.fountainizer.parser.meta.FCharacter;

public class CharacterPager extends AbstractPager<List<FCharacter>> {

    CharacterPager(PagerController controller, Class<? extends AbstractPager<?>> type) throws IOException {
	super(controller);
	super.type = type;
    }

    @Override
    public void printContent(List<FCharacter> t) throws IOException {
	float xLines = 400;
	printHeader();
	super.nextLine();
	super.fontSize = 14;
	super.printString("Name", super.xPos, super.yPos, getItalicFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	super.printString("Takes", super.xPos+xLines, yPos, getItalicFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	nextLine(5f);
	super.fontSize = null;
	for (FCharacter c : t) {
	    super.printString(c.getName(), super.xPos, super.yPos, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	    super.printString(Integer.toString(c.getTakes()), super.xPos+xLines, super.yPos, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	    nextLine(15f);
	}

    }

    private void printHeader() throws IOException {
	super.fontSize = 18;
	super.printString("Character Index", super.xPos, super.yPos, getFont(), getFontSize(), PagerController.STANDARD_TEXT_COLOR);
	super.nextLine(30f);
	super.fontSize = null;
    }

}
