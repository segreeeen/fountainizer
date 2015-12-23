package at.hacksolutions.f2p.parser;

public class Lines {
    private Line[] lines;
    private int lineCount;
    private boolean[] emptyLines;

    public Lines(Line[] lines, boolean[] emptyLines) {
	this.lines = lines;
	this.emptyLines = emptyLines;
	this.lineCount = lines.length;
    }

    public Line get(int index) {
	return (index >= 0 && index < lineCount) ? lines[index] : null;
    }

    public int getLineCount() {
	return lineCount;
    }

    public boolean prevIsEmpty(Line l) {
	if ((l.getLineNr() - 1) > 0)
	    return emptyLines[l.getLineNr() - 1];
	return false;
    }

    public boolean nextIsEmpty(Line l) {
	if ((l.getLineNr() + 1) < this.lineCount)
	    return emptyLines[l.getLineNr() + 1];
	return false;
    }
}
