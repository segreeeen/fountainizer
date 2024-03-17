package at.hsol.fountainizer.ui.simplegui.simpleGuiv1.helper;


import at.hsol.fountainizer.core.api.parser.Options;
import at.hsol.fountainizer.ui.simplegui.simpleGuiv1.log.Dump;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Settings {
	private static Settings instance = null;

	private static final String FILENAME = "settings.fu";

	private final Properties propz;

	private Settings() {
		propz = new Properties();

		try {
			String filePath = System.getProperty("user.dir");
			propz.loadFromXML(new FileInputStream(filePath + File.separator + FILENAME));

		} catch (Exception e) {
			Dump.thatShit("Settings file does not exist or is corrupted. Creating new one!", e);
			store(new Options());
		}

	}

	public void store(Options o) {
		propz.setProperty("printTakeNumbers", Boolean.toString(o.printTakeNumber()));
		propz.setProperty("printPageNumbers", Boolean.toString(o.printPageNumber()));
		propz.setProperty("printTitlePage", Boolean.toString(o.printTitlePage()));
		propz.setProperty("printCharacterPage", Boolean.toString(o.printCharacterPage()));
		propz.setProperty("customCharacterScript", Boolean.toString(o.customCharacterScript()));
		propz.setProperty("sortCharacters", o.sortCharacters().toString());
		propz.setProperty("customCharacter", o.getCustomCharacter());
	}

	public Options load() {
		boolean printTakeNumbers = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		boolean printPageNumbers = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		boolean printTitlePage = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		boolean printCharacterPage = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		boolean customCharacterScript = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		Options.SortMode sortCharacters = Options.SortMode.valueOf(propz.getProperty("sortCharacters"));
		String customCharacter = propz.getProperty("customCharacter");
		return new Options(printTakeNumbers, printPageNumbers, printTitlePage, printCharacterPage,
				customCharacterScript, sortCharacters, customCharacter);
	}

	public static synchronized Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return Settings.instance;
	}

}
