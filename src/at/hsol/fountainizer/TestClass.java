package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestClass {
    public static void main(String[] args) throws URISyntaxException {
	try {

	    FountainizerHelper helper = new FountainizerHelper("D:\\Eigene Dateien\\Documents\\test.txt", 
		    "D:\\Eigene Dateien\\Documents\\test.pdf");
	    
	    helper.read();
	    helper.parse();
	    helper.printPdf();
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
    }
} 
