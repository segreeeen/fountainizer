package at.hacksolutions.f2p.io;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;

import at.hacksolutions.f2p.parser.line.ParserLine;
import at.hacksolutions.f2p.parser.line.ParserLines;
import at.hacksolutions.f2p.parser.types.TitleLineType;
import at.hacksolutions.f2p.pdfbox.Pager;
import at.hacksolutions.f2p.pdfbox.Paragraph;
import at.hacksolutions.f2p.pdfbox.StandardPager;
import at.hacksolutions.f2p.pdfbox.TitlePager;

public class FilePrinter {

    public static void writePDFBox(ParserLines dLines, String filename)
	    throws IOException, COSVisitorException {
	PDDocument doc = new PDDocument();
	Pager titlePager = new TitlePager(doc, 60, 40, 40, 60);
	Pager standardPager = new StandardPager(doc, 60, 40, 40,60);

	for (ParserLine line : dLines) {
	    LinkedList<Paragraph> paragraphs = line.getParagraphForPDF();
	    if (line.getLineType().getClass() == TitleLineType.class) {
		if (paragraphs != null) {
		    for (Paragraph p : paragraphs) {
			titlePager.drawParagraph(p);
		    }
		}
	    } else {
		if (paragraphs != null) {
		    for (Paragraph p : paragraphs) {
			standardPager.drawParagraph(p);
		    }
		}
	    }
	}
	standardPager.finalize(filename);

    }
}
