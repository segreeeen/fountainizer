package at.hsol.fountainizer;

public class Options {
	public static final int SORT_BY_NAME = 1;
	public static final int SORT_BY_TAKES = 2;

	private boolean printTakeNumbers;
	private boolean printPageNumbers;
	private boolean printTitlePage;
	private boolean printCharacterPage;
	private boolean customCharacterScript;
	private int sortCharacters;
	private String customCharacter;

	public Options() {
		this.printPageNumbers = true;
		this.printTakeNumbers = true;
		this.sortCharacters = SORT_BY_NAME;
		this.printCharacterPage = true;
		this.customCharacter = "amos";
	}

	public boolean printTakeNumber() {
		return printTakeNumbers;
	}

	public void setPrintTakeNumbers(boolean printTakeNumbers) {
		this.printTakeNumbers = printTakeNumbers;
	}

	public boolean printPageNumber() {
		return printPageNumbers;
	}

	public void setPrintPageNumbers(boolean printPageNumbers) {
		this.printPageNumbers = printPageNumbers;
	}

	public int sortCharacters() {
		return sortCharacters;
	}

	public void setSortCharacters(int sortCharacters) {
		this.sortCharacters = sortCharacters;
	}

	public boolean printTitlePage() {
		return printTitlePage;
	}

	public void setPrintTitlePage(boolean printTitlePage) {
		this.printTitlePage = printTitlePage;
	}

	public boolean printCharacterPage() {
		return printCharacterPage;
	}

	public void setPrintCharacterPage(boolean printCharacterPage) {
		this.printCharacterPage = printCharacterPage;
	}

	public String getCustomCharacter() {
		return customCharacter;
	}

	public void setCustomCharacter(String customCharacter) {
		this.customCharacter = customCharacter;
	}

	public boolean customCharacterScript() {
		return customCharacterScript;
	}

	public void setCustomCharacterScript(boolean customCharacterScript) {
		this.customCharacterScript = customCharacterScript;
	}

}
