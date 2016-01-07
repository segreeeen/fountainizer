package at.hsol.fountainizer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import at.hsol.fountainizer.parser.data.Characters;

public class TestClass {
    public static void main(String[] args) throws URISyntaxException {
//	try {
//
//	    FountainizerHelper helper = new FountainizerHelper("D://git//fountain2pdf//samples//bigfish.txt", 
//		    "D://git//fountain2pdf//samples//bigfish.pdf");
//	    
//	    helper.read();
//	    helper.parse();
//	    helper.printPdf();
//	    
//	} catch (IOException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	} 
	Characters c = new Characters();
	String huh = c.add("boris = b");
	c.add("b=k");
	System.out.println(c.lookup("b=k"));
    }
} 
