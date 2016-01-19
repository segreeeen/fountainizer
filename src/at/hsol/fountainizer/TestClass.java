package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;

public class TestClass {
    public static void main(String[] args) throws URISyntaxException {
	try {

	    FountainizerHelper helper = new FountainizerHelper("D:\\Eigene Dateien\\Documents\\DasUrteil.txt", 
		    "D:\\Eigene Dateien\\Documents\\DasUrteil.pdf");
	    
	    helper.read();
	    helper.parse();
	    helper.printPdf();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	} 
    }
} 
