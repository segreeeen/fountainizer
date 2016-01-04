package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestClass {
    public static void main(String[] args) throws URISyntaxException {
	try {

	    FountainizerHelper helper = new FountainizerHelper("D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\bigfish.txt", 
		    "D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\bigfish.pdf");
	    
	    helper.read();
	    helper.parse();
	    helper.printPdf();
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
    }
} 
