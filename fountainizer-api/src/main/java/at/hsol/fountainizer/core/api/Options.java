package at.hsol.fountainizer.core.api;

public class Options {
	public enum SortMode {
		BY_NAME, BY_TAKE_COUNT
	}

	private boolean printTakeNumbers;
	private boolean printPageNumbers;
	private boolean printTitlePage;
	private boolean printCharacterPage;
	private boolean customCharacterScript;
	private SortMode sortCharacters;
	private String customCharacter;

	public Options() {
		this.printPageNumbers = true;
		this.printTakeNumbers = true;
		this.sortCharacters = SortMode.BY_NAME;
		this.printCharacterPage = true;
		this.customCharacter = null;
		this.customCharacterScript = false;
	}

	public Options(boolean printTakeNumbers, boolean printPageNumbers, boolean printTitlePage,
                   boolean printCharacterPage, boolean customCharacterScript, SortMode sortCharacters,
                   String customCharacter) {
		this.printTakeNumbers = printTakeNumbers;
		this.printPageNumbers = printPageNumbers;
		this.printTitlePage = printTitlePage;
		this.printCharacterPage = printCharacterPage;
		this.customCharacterScript = customCharacterScript;
		this.sortCharacters = sortCharacters;
		this.customCharacter = customCharacter;
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

	public SortMode sortCharacters() {
		return sortCharacters;
	}

	public void setSortCharacters(SortMode sortCharacters) {
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
