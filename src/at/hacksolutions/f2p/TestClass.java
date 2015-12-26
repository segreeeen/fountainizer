package at.hacksolutions.f2p;

import java.io.IOException;

import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.line.Line;
import at.hacksolutions.f2p.parser.line.Lines;

public class TestClass {
    public static void main(String[] args) {
	try {
	    Lines lines = FileReader.getLines("D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\sample.txt");
	    Parser.parse(lines);
	    Line l6 = lines.get(6);
	    Line l7 = lines.get(7);
	    System.out.println(lines.pEmptyText(l6));
	    System.out.println(l6.getText());
	    System.out.println(lines.nEmptyText(l6));
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
