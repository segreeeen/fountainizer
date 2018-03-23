package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestClass {
	public static void main(String[] args) throws URISyntaxException {
		try {
			Options o = new Options();
			o.setCustomCharacter("könig");
			o.setCustomCharacterScript(true);
			o.setPrintCharacterPage(true);
			o.setPrintTitlePage(true);
			o.setPrintPageNumbers(true);
			o.setPrintTakeNumbers(true);
			o.setSortCharacters(Options.SortMode.BY_TAKE_COUNT);
			FountainizerHelper helper = new FountainizerHelper("F:\\git\\fountainizer_github\\samples\\DasUrteil.txt",
					"F:\\git\\fountainizer_github\\samples\\DasUrteil.pdf", o);

			helper.read();
			helper.parse();
			helper.printPdf();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
