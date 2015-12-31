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
import at.hacksolutions.f2p.parser.line.ParserLine;

@SuppressWarnings("unused")
public class TestClass {
    public static void main(String[] args) {
	try {
	    Long time = System.currentTimeMillis();
	    DynamicLines lines = FileReader.getLines(
		    "D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\sample.txt");
	    
	    
	    Parser.parse(lines);
	    
	    ParserLine l = lines.get(lines.getLineCount());
	    
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
