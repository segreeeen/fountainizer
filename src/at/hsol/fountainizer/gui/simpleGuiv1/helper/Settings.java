package at.hsol.fountainizer.gui.simpleGuiv1.helper;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.gui.simpleGuiv1.log.Dump;

public class Settings {
	private static Settings instance = null;

	private final String filePath = System.getProperty("user.dir");
	private static final String FILENAME = "settings.fu";

	private Properties propz;

	private Settings() {
		propz = new Properties();

		try {
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
		propz.setProperty("sortCharacters", Integer.toString(o.sortCharacters()));
		propz.setProperty("customCharacter", o.getCustomCharacter());
	}

	public Options load() {
		boolean printTakeNumbers = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		boolean printPageNumbers = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		boolean printTitlePage = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		boolean printCharacterPage = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		boolean customCharacterScript = Boolean.getBoolean(propz.getProperty("printTakeNumbers"));
		int sortCharacters = Integer.parseInt(propz.getProperty("sortCharacters"));
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