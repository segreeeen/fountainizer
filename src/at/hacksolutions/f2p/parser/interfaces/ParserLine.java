package at.hacksolutions.f2p.parser.interfaces;

import java.util.LinkedList;

import at.hacksolutions.f2p.pdfbox.paragraph.Paragraph;

/**
 * @author Felix Batusic
 */
public interface ParserLine {

    /**
     * @return Paragraph for the PDF printer.
     */
    public LinkedList<Paragraph> getParagraphForPDF();

    /**
     * @get LineType
     */
    public ParserType getLineType();

    public void setLineType(ParserType type);

    public int getLineNr();

    public void setDualDialogue(boolean b);

    public boolean emptyText();

    public String getText();

    public void decLineNr();

    public void incLineNr();

}
