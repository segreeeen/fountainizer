package at.hacksolutions.f2p.io;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;

import at.hacksolutions.f2p.parser.line.SimpleLine;
import at.hacksolutions.f2p.parser.line.ParserLine;
import at.hacksolutions.f2p.parser.line.ParserLines;
import at.hacksolutions.f2p.pdfbox.Pager;
import at.hacksolutions.f2p.pdfbox.Paragraph;
import at.hacksolutions.f2p.pdfbox.RichFormat;
import at.hacksolutions.f2p.pdfbox.RichString;

public class FilePrinter {

    public static void writePDFBox(ParserLines dLines, String filename)
	    throws IOException, COSVisitorException {
	PDDocument doc = new PDDocument();
	Pager mypage = new Pager(doc, 60, 40, 40, 60); 

	Paragraph titleParagraph = new Paragraph(
		new RichString("test", new RichFormat()));
	titleParagraph.setCentered(true);
	titleParagraph.setUnderlined(true);
	titleParagraph.setMarginTop(
		(mypage.getPageHeight() / 2) - mypage.getLineHeight());

	Paragraph authorParagraph = new Paragraph(
		new RichString("testautor", new RichFormat()));
	authorParagraph.setCentered(true);
	authorParagraph.setMarginBottom(
		(mypage.getPageHeight() / 2) - mypage.getLineHeight());

	mypage.drawParagraph(titleParagraph);
	mypage.drawParagraph(authorParagraph);

	for (ParserLine line : dLines) {

	    Paragraph p = line.getParagraphForPDF();
	    if (p != null) {
		mypage.drawParagraph(p);
	    }
	}
	mypage.finalize(filename);

    }
}
