package at.hacksolutions.f2p.io;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;

import at.hacksolutions.f2p.parser.line.ParserLine;
import at.hacksolutions.f2p.parser.line.ParserLines;
import at.hacksolutions.f2p.parser.types.TitleLineType;
import at.hacksolutions.f2p.pdfbox.StandardPager;
import at.hacksolutions.f2p.pdfbox.Paragraph;
import at.hacksolutions.f2p.pdfbox.RichFormat;
import at.hacksolutions.f2p.pdfbox.RichString;

public class FilePrinter {

    public static void writePDFBox(ParserLines dLines, String filename)
	    throws IOException, COSVisitorException {
	PDDocument doc = new PDDocument();
	StandardPager mypage = new StandardPager(doc, 60, 40, 40, 60);

	Paragraph titleParagraph = new Paragraph(
		new RichString("test", new RichFormat()));
	titleParagraph.setCentered(true);
	titleParagraph.setUnderlined(true);
	titleParagraph.setMarginTop(
		(mypage.getPageHeight() / 2) - mypage.getLineHeight());

	Paragraph authorParagraph = new Paragraph(
		new RichString("testautor", new RichFormat()));
	authorParagraph.setMarginLeft(10);
	authorParagraph.setMarginTop(
		((mypage.getPageHeight() / 4)) - mypage.getLineHeight());
	
	mypage.drawParagraph(titleParagraph);
	mypage.drawParagraph(authorParagraph);
	
	mypage.initNextPage();

	for (ParserLine line : dLines) {
	    if (line.getLineType().getClass() == TitleLineType.class) {
		
	    }
	    LinkedList<Paragraph> paragraphs = line.getParagraphForPDF();
	    if (paragraphs != null) {
		for (Paragraph p : paragraphs) {

		    mypage.drawParagraph(p);

		}
	    }
	}
	mypage.finalize(filename);

    }
}
