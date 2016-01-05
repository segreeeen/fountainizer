package at.hsol.fountainizer.parser.interfaces;

import java.util.LinkedList;

import at.hsol.fountainizer.pdfbox.paragraph.Paragraph;

/**
 * @author Felix Batusic
 */
public interface ParserLine {

    /**
     * @return Paragraph for the PDF printer.
     */
    public LinkedList<Paragraph> getParagraphForPDF();

    public ParserType getLineType();

    public boolean emptyText();

    public String getText();

}
