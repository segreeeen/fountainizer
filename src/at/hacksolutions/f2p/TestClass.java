package at.hacksolutions.f2p;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;

import at.hacksolutions.f2p.io.FilePrinter;
import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.line.Line;
import at.hacksolutions.f2p.parser.line.Lines;

public class TestClass {
    public static void main(String[] args) {
	try {
	    Lines lines = FileReader.getLines("D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\sample.txt");
	    Parser.parse(lines);
	    FilePrinter.writePDFBox(lines, "testfile.pdf");
	    
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (COSVisitorException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
