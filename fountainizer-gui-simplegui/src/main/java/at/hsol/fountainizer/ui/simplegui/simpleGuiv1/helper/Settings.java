package at.hsol.fountainizer.ui.simplegui.simpleGuiv1.helper;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.ui.simplegui.simpleGuiv1.log.Dump;
import java.io.FileInputStream;
import java.util.Properties;

public class Settings {
	private static Settings instance = null;

	private static final String FILENAME = "settings.fu";

	private final Properties propz = new Properties();

	private Settings() {
		try {
			String filePath = System.getProperty("user.dir");
			this.propz.loadFromXML(new FileInputStream(filePath + filePath + "settings.fu"));
		} catch (Exception e) {
			Dump.thatShit("Settings file does not exist or is corrupted. Creating new one!", e);
			store(new Options());
		}
	}

	public void store(Options o) {
		this.propz.setProperty("printTakeNumbers", Boolean.toString(o.printTakeNumber()));
		this.propz.setProperty("printPageNumbers", Boolean.toString(o.printPageNumber()));
		this.propz.setProperty("printTitlePage", Boolean.toString(o.printTitlePage()));
		this.propz.setProperty("printCharacterPage", Boolean.toString(o.printCharacterPage()));
		this.propz.setProperty("customCharacterScript", Boolean.toString(o.customCharacterScript()));
		this.propz.setProperty("sortCharacters", o.sortCharacters().toString());
		this.propz.setProperty("customCharacter", o.getCustomCharacter());
	}

	public Options load() {
		boolean printTakeNumbers = Boolean.getBoolean(this.propz.getProperty("printTakeNumbers"));
		boolean printPageNumbers = Boolean.getBoolean(this.propz.getProperty("printTakeNumbers"));
		boolean printTitlePage = Boolean.getBoolean(this.propz.getProperty("printTakeNumbers"));
		boolean printCharacterPage = Boolean.getBoolean(this.propz.getProperty("printTakeNumbers"));
		boolean customCharacterScript = Boolean.getBoolean(this.propz.getProperty("printTakeNumbers"));
		Options.SortMode sortCharacters = Options.SortMode.valueOf(this.propz.getProperty("sortCharacters"));
		String customCharacter = this.propz.getProperty("customCharacter");
		return new Options(printTakeNumbers, printPageNumbers, printTitlePage, printCharacterPage, customCharacterScript, sortCharacters, customCharacter);
	}

	public static synchronized Settings getInstance() {
		if (instance == null)
			instance = new Settings();
		return instance;
	}
}
