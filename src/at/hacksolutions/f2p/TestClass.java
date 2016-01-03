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

	    DynamicLines lines = FileReader.getLines(
		    "D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\bigfish.txt");
	    
	    Long time = System.currentTimeMillis();
	    Parser.parse(lines);
	    time = (System.currentTimeMillis() - time);
	    System.out.println(time);

	    FilePrinter.writePDFBox(lines, "testfile2.pdf");
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
    }
}
