package at.hsol.fountainizer.pdfbox;

public class PagerOptions {
    private boolean printTakeNumbers;
    private boolean printPageNumbers;
        
    public PagerOptions(boolean printTakeNumbers, boolean printPageNumbers) {
	this.printPageNumbers = printPageNumbers;
	this.printTakeNumbers = printTakeNumbers;
    }
    
    public PagerOptions() {
	this(true, true);
    }

    public boolean printTakeNumbers() {
        return printTakeNumbers;
    }

    public boolean printPageNumbers() {
        return printPageNumbers;
    }
    
    
}
