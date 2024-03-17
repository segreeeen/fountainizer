package at.hsol.fountainizer.printer.pdf.pager;

import java.io.IOException;

final class PagerFactory {

	static AbstractPager<?> getPager(Class<? extends AbstractPager<?>> PAGER_TYPE, PagerController controller)
			throws IOException {
		if (PAGER_TYPE == PagerController.PagerType.STANDARD_PAGER) {
			return new StandardPager(controller, PAGER_TYPE);
		} else if (PAGER_TYPE == PagerController.PagerType.CHARACTER_PAGER) {
			return new CharacterPager(controller, PAGER_TYPE);
		} else if (PAGER_TYPE == PagerController.PagerType.TITLE_PAGER) {
			return new TitlePager(controller, PAGER_TYPE);
		} else {
			return null;
		}
	}

}
