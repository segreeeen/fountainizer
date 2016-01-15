package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import at.hsol.fountainizer.parser.meta.FCharacter;

public class TestClass {
    public static void main(String[] args) throws URISyntaxException {
	try {

	    FountainizerHelper helper = new FountainizerHelper("D:\\Eigene Dateien\\Documents\\DasUrteil.txt", 
		    "D:\\Eigene Dateien\\Documents\\DasUrteil.pdf");
	    
	    helper.read();
	    helper.parse();
	    helper.printPdf();
	    List<FCharacter> characters = helper.getCharacterStats();
	    for (FCharacter e: characters) {
		System.out.println(e.getName()+": "+e.getTakes() + " Lines");
	    }
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
    }
} 
