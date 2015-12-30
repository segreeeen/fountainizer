package at.hacksolutions.f2p;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;

import at.hacksolutions.f2p.io.FilePrinter;
import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.line.SimpleLine;
import at.hacksolutions.f2p.parser.line.ParserLines;
import at.hacksolutions.f2p.parser.line.DynamicLines;
import at.hacksolutions.f2p.parser.line.FixedLines;

@SuppressWarnings("unused")
public class TestClass {
    public static void main(String[] args) {
	try {
	    Long time = System.currentTimeMillis();
	    FixedLines lines = FileReader.getLines(
		    "D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\bigfish.txt");
	    
//	    DynamicLines dLines = lines.asDynamicLines();
//	    
//	    dLines.add(
//		    "This is the last line, and it is an ACTION\n line, and new.");


	    Parser.parse(lines);
	    time = (System.currentTimeMillis() - time);
	    FilePrinter.writePDFBox(lines, "testfile2.pdf");

	    System.out.println(time);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (COSVisitorException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
