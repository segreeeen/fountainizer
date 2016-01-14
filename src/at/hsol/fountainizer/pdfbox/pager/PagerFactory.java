package at.hsol.fountainizer.pdfbox.pager;

import at.hsol.fountainizer.pdfbox.pager.PageController.PagerType;

final class PagerFactory {

    static AbstractPager<?> getPager(PagerType PAGER_TYPE, PageController controller) {
	if (PAGER_TYPE == PageController.PagerType.STANDARD_PAGER) {
	    return new StandardPager(controller);
	} else if (PAGER_TYPE == PageController.PagerType.CHARACTER_PAGER) {
	    return new CharacterPager(controller);
	} else if (PAGER_TYPE == PageController.PagerType.TITLE_PAGER) {
	    return new TitlePager(controller);
	} else {
	    return null;
	}
    }

}
