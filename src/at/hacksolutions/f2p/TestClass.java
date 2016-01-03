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

	    FountainizerHelper helper = new FountainizerHelper("D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\bigfish.txt", 
		    "D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\bigfish.pdf");
	    
	    Long time = System.currentTimeMillis();
	    helper.read();
	    helper.parse();
	    helper.printPdf();
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
    }
}
