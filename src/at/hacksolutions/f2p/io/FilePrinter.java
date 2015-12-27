package at.hacksolutions.f2p.io;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;

import at.hacksolutions.f2p.parser.line.GeneralLine;
import at.hacksolutions.f2p.parser.line.Lines;
import at.hacksolutions.f2p.pdfbox.*;

public class FilePrinter {

    public static void writePDFBox(Lines lines, String filename)
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

	for (GeneralLine line : lines) {

	    Paragraph p = line.getParagraphForPDF();
	    if (p != null) {
		mypage.drawParagraph(p);
	    }
	}
	mypage.finalize(filename);

    }
}
