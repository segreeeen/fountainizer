package at.hacksolutions.f2p.parser.line;
import java.util.LinkedList;
import at.hacksolutions.f2p.pdfbox.Paragraph;

public interface ParserLine {
    
    /**
     * @return Paragraph for the PDF printer.
     */
    public LinkedList<Paragraph> getParagraphForPDF();

}
