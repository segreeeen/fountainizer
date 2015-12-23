package at.hacksolutions.f2p.parser;

import java.util.Iterator;
import java.util.ArrayList;

public class Lines implements Iterable<Line>{
    private ArrayList<Line> lines;
    private int lineCount;
    
    public Lines() {
	this.lines = new ArrayList<Line>();
	this.lineCount = 0;
    }
    
    public void add(Line line) {
	lines.add(line);
	this.lineCount++;
    }

    @Override
    public Iterator<Line> iterator() {
	return lines.iterator();
    }

    public int getLineCount() {
	return lineCount;
    }
    
    public Line[] toArray() {
	return lines.toArray(new Line[]{});
    }
}
