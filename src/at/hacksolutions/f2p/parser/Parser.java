package at.hacksolutions.f2p.parser;

public class Parser {
    private Lines outputLines;
    private boolean[] emptyLines;
    
    
    public Parser(Lines inputLines, boolean[] emptyLines) {
	this.outputLines = inputLines;
	this.emptyLines = emptyLines;
    }
    
    public Lines parse() {
	boolean[] emptyLines = new boolean[outputLines.getLineCount()];
	makeELTable(emptyLines);
	return outputLines;
    }
    
    public void makeELTable(boolean[] emptyLines) {
	
    }
}
