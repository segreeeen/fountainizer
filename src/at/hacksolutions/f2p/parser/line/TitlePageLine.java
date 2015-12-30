package at.hacksolutions.f2p.parser.line;

import at.hacksolutions.f2p.pdfbox.Paragraph;

/**
 * @author SeGreeeen
 * This Line is used as a substitute for all the title Lines and in the end is
 * printed as a Title Page. It consists of a List of TitlePageLines that are 
 * found in the first run of parsing. It does not contain any actual text.
 */
public class TitlePageLine implements ParserLine {

    @Override
    public Paragraph getParagraphForPDF() {
	// TODO Auto-generated method stub
	return null;
    }

}
