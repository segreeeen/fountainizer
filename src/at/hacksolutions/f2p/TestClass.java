package at.hacksolutions.f2p;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;

import at.hacksolutions.f2p.io.FilePrinter;
import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.interfaces.ParserLine;
import at.hacksolutions.f2p.parser.interfaces.ParserLines;
import at.hacksolutions.f2p.parser.line.SimpleLine;
import at.hacksolutions.f2p.parser.line.TitlePage;
import at.hacksolutions.f2p.pdfbox.paragraph.Paragraph;
import at.hacksolutions.f2p.pdfbox.paragraph.RichString;
import at.hacksolutions.f2p.parser.line.DynamicLines;

@SuppressWarnings("unused")
public class TestClass {
    public static void main(String[] args) {
	try {
	    Long time = System.currentTimeMillis();
	    DynamicLines lines = FileReader.getLines(
		    "D:\\git\\fountain2pdf\\src\\at\\hacksolutions\\f2p\\sample.txt");
	    
	    
	    Parser.parse(lines);
	    

	    FilePrinter.writePDFBox(lines, "testfile2.pdf");
	    time = (System.currentTimeMillis() - time);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (COSVisitorException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
