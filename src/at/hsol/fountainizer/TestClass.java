package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TestClass {
    public static void main(String[] args) throws URISyntaxException {
	try {

	    FountainizerHelper helper = new FountainizerHelper("D:\\Eigene Dateien\\Documents\\DasUrteil.txt", 
		    "D:\\Eigene Dateien\\Documents\\DasUrteil.pdf");
	    
	    helper.read();
	    helper.parse();
	    helper.printPdf();
	    TreeMap<String,Integer> characters = helper.getStats().getCharacterStats();
	    for (Entry<String, Integer> e: characters.entrySet()) {
		System.out.println(e.getKey()+": "+e.getValue() + " Lines");
	    }
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} 
    }
} 
