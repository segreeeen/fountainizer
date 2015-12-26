package at.hacksolutions.f2p.parser;

import java.util.ArrayDeque;
import java.util.Deque;

public class LineTags {
    private Deque<LineTagType> startTags;
    private Deque<LineTagType> endTags;
    
    public LineTags() {
	startTags = new ArrayDeque<LineTagType>();
	endTags = new ArrayDeque<LineTagType>();
    }
    
    public void pushStart(LineTagType type) {
	startTags.push(type);
    }
    
    public LineTagType popStart() {
	    return startTags.poll();
    }
    
    public void pushEnd(LineTagType type) {
	endTags.push(type);
    }
    
    public LineTagType popEnd() {
	    return endTags.poll();
    }
}
