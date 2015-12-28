package at.hacksolutions.f2p;

import java.io.IOException;

import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.line.DynamicLines;
import at.hacksolutions.f2p.parser.line.FixedLines;

public class TestClass {
    public static void main(String[] args) throws IOException {
	    FixedLines lines = FileReader.getLines("D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\sample.txt");

	    DynamicLines dLines = lines.asDynamicLines();
	    dLines.add("This is the last line, and it is an ACTION line, and new.");
	    Parser.parse(dLines);
	    System.out.println("lol");
    }
}
