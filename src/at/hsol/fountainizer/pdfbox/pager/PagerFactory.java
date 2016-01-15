package at.hsol.fountainizer.pdfbox.pager;

import java.io.IOException;

import at.hsol.fountainizer.pdfbox.pager.PagerController.PagerType;

final class PagerFactory {

    static AbstractPager<?> getPager(PagerType PAGER_TYPE, PagerController controller) throws IOException {
	if (PAGER_TYPE == PagerController.PagerType.STANDARD_PAGER) {
	    return new StandardPager(controller);
	} else if (PAGER_TYPE == PagerController.PagerType.CHARACTER_PAGER) {
	    return new CharacterPager(controller);
	} else if (PAGER_TYPE == PagerController.PagerType.TITLE_PAGER) {
	    return new TitlePager(controller);
	} else {
	    return null;
	}
    }

}
