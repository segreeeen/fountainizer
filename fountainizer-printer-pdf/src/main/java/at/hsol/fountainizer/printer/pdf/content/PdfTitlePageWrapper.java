package at.hsol.fountainizer.printer.pdf.content;

import at.hsol.fountainizer.core.api.parser.Line;
import at.hsol.fountainizer.core.api.types.LineType;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PdfTitlePageWrapper {
    private final Map<LineType, List<Line>> titlePageLines;

    public PdfTitlePageWrapper(Map<LineType, List<Line>> titlePage) {
        this.titlePageLines = titlePage;
    }

    public List<Paragraph> getParagraphForPDF(LineType type) {
        LinkedList<Paragraph> paragraphs = new LinkedList<>();
        if (this.titlePageLines.containsKey(type))
            return titlePageLines.get(type).stream()
                    .map(line -> new PdfLineWrapper(line).getParagraphForPDF())
                    .flatMap(Collection::stream)
                    .toList();
        return null;
    }

    public List<Line> getLines(LineType t) {
        return this.titlePageLines.get(t);
    }

    public boolean contains(LineType t) {
        return this.titlePageLines.containsKey(t);
    }

    public boolean emptyText() {
        return this.titlePageLines.isEmpty();
    }

    public boolean isEmpty() {
        return this.titlePageLines.isEmpty();
    }
}
