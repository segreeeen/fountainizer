package at.hsol.fountainizer.printer.pdf.content;

import at.hsol.fountainizer.core.api.parser.Line;
import at.hsol.fountainizer.core.api.parser.StylizedText;
import at.hsol.fountainizer.core.api.types.HeadingType;
import at.hsol.fountainizer.core.api.types.LineType;
import java.util.LinkedList;

public class PdfLineWrapper implements Line {
    private final Line line;

    public PdfLineWrapper(Line line) {
        this.line = line;
    }

    public LinkedList<Paragraph> getParagraphForPDF() {
        LineTypeMarginDecorator margins = LineTypeMarginDecorator.getFromLineType(this.line.getLineType());
        if (getText() != null) {
            Paragraph p = new Paragraph(this.line.getRichString());
            p.setLinetype(this.line.getLineType());
            p.setUppercase(margins.isUppercase());
            p.setUnderlined(margins.isUnderlined());
            p.setCentered(margins.isCentered());
            p.setMargin(margins.getMarginTop(), margins.getMarginLeft(), margins.getMarginRight(), margins.getMarginBottom());
            LinkedList<Paragraph> linkedList = new LinkedList<>();
            p.setLineTypeNumber(getLineTypeNumber());
            p.setDualDialogue(isDualDialogue());
            linkedList.add(p);
            return linkedList;
        }
        LinkedList<Paragraph> paragraphs = new LinkedList<>();
        paragraphs.add(Paragraph.getEmptyParagraph());
        return paragraphs;
    }

    public LineType getLineType() {
        return this.line.getLineType();
    }

    public HeadingType getHeadingType() {
        return this.line.getHeadingType();
    }

    public int getLineNr() {
        return this.line.getLineNr();
    }

    public boolean emptyText() {
        return this.line.emptyText();
    }

    public boolean isDualDialogue() {
        return this.line.isDualDialogue();
    }

    public int getLineTypeNumber() {
        return this.line.getLineTypeNumber();
    }

    public String getText() {
        return this.line.getText();
    }

    public Line getPrev() {
        return this.line.getPrev();
    }

    public Line getNext() {
        return this.line.getNext();
    }

    public boolean hasNext() {
        return this.line.hasNext();
    }

    public boolean hasPrev() {
        return this.line.hasPrev();
    }

    public StylizedText getRichString() {
        return this.line.getRichString();
    }
}
