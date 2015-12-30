package at.hacksolutions.f2p.parser.line;
import at.hacksolutions.f2p.pdfbox.Paragraph;

public interface ParserLine {
    
    /**
     * @return Paragraph for the PDF printer.
     */
    public Paragraph getParagraphForPDF();

}
