package at.hacksolutions.f2p;

import java.io.IOException;
import java.net.URISyntaxException;

import at.hacksolutions.f2p.io.FilePrinter;
import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.line.DynamicLines;

public class TestClass {
    public static void main(String[] args) throws URISyntaxException {
	try {
	    Long time = System.currentTimeMillis();
	    DynamicLines lines = FileReader.getLines(
		    "D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\sample.txt");
	    
	    
	    Parser.parse(lines);
	    

	    FilePrinter.writePDFBox(lines, "testfile2.pdf");
	    time = (System.currentTimeMillis() - time);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
    }
}
