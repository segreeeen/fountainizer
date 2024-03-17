package at.hsol.fountainizer.pdf.content;

import at.hsol.fountainizer.core.parser.api.Line;
import at.hsol.fountainizer.core.parser.FountainEmphasizedText;
import at.hsol.fountainizer.core.parser.api.HeadingType;
import at.hsol.fountainizer.core.parser.api.LineType;

import java.util.LinkedList;

public class PDFLineWrapper implements Line {

    private final Line line;

    public PDFLineWrapper(Line line) {
        this.line = line;
    }

    public LinkedList<Paragraph> getParagraphForPDF() {
        LineTypeMarginDecorator margins = LineTypeMarginDecorator.getFromLineType(line.getLineType());

        if (getText() != null) {
            Paragraph p = new Paragraph(this.line.getRichString());
            p.setLinetype(line.getLineType());
            p.setUppercase(margins.isUppercase());
            p.setUnderlined(margins.isUnderlined());
            p.setCentered(margins.isCentered());
            p.setMargin(margins.getMarginTop(), margins.getMarginLeft(), margins.getMarginRight(), margins.getMarginBottom());
            LinkedList<Paragraph> paragraphs = new LinkedList<>();
            p.setLineTypeNumber(getLineTypeNumber());
            p.setDualDialogue(isDualDialogue());
            paragraphs.add(p);
            return paragraphs;
        } else {
            LinkedList<Paragraph> paragraphs = new LinkedList<>();
            paragraphs.add(Paragraph.getEmptyParagraph());
            return paragraphs;
        }
    }

    @Override
    public LineType getLineType() {
        return this.line.getLineType();
    }

    @Override
    public HeadingType getHeadingType() {
        return this.line.getHeadingType();
    }

    @Override
    public int getLineNr() {
        return line.getLineNr();
    }

    @Override
    public boolean emptyText() {
        return line.emptyText();
    }


    @Override
    public boolean isDualDialogue() {
        return line.isDualDialogue();
    }

    @Override
    public int getLineTypeNumber() {
        return line.getLineTypeNumber();
    }


    @Override
    public String getText() {
        return line.getText();
    }

    @Override
    public Line getPrev() {
        return line.getPrev();
    }

    @Override
    public Line getNext() {
        return line.getNext();
    }

    @Override
    public boolean hasNext() {
        return line.hasNext();
    }

    @Override
    public boolean hasPrev() {
        return line.hasPrev();
    }

    @Override
    public FountainEmphasizedText getRichString() {
        return this.line.getRichString();
    }
}
