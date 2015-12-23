package com.hacksolutions.f2p.parser;

public class Parser {
    private Lines outputLines;
    
    
    public Parser(Lines inputLines) {
	this.outputLines = inputLines;
    }
    
    public Lines parse() {
	boolean[] emptyLines = new boolean[outputLines.getLineCount()];
	makeELTable(emptyLines);
	return outputLines;
    }
    
    public void makeELTable(boolean[] emptyLines) {
	
    }
}
