package at.hsol.fountainizer.pdfbox;

public class PagerOptions {
    private final boolean printTakeNumbers;
    private final boolean printPageNumbers;
    
    public PagerOptions(boolean printTakeNumbers, boolean printPageNumbers) {
	this.printPageNumbers = printPageNumbers;
	this.printTakeNumbers = printTakeNumbers;
    }

    public boolean printTakeNumbers() {
        return printTakeNumbers;
    }

    public boolean printPageNumbers() {
        return printPageNumbers;
    }
    
}
