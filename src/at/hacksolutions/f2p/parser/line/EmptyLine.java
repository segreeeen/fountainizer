package at.hacksolutions.f2p.parser.line;

import java.util.LinkedList;

import at.hacksolutions.f2p.parser.types.LineType;
import at.hacksolutions.f2p.parser.types.ParserType;
import at.hacksolutions.f2p.pdfbox.Paragraph;

public class EmptyLine implements ParserLine{
    LineType type = LineType.EMPTY;

    @Override
    public LinkedList<Paragraph> getParagraphForPDF() {
	return null;
    }

    @Override
    public ParserType getLineType() {
	return type;
    }

    @Override
    public int getLineNr() {
	return 0;
    }

    @Override
    public void setDualDialogue(boolean b) {}

    @Override
    public boolean emptyText() {
	return true;
    }

    @Override
    public String getText() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setLineType(ParserType type) {
	// TODO Auto-generated method stub
	
    }

}
