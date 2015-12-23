package at.hacksolutions.f2p.parser;

public class SubLine {
    private final SubLineType type;
    private final String text;
    
    public SubLine(SubLineType type, String text) {
	this.type = type;
	this.text = text;
    }
    
    public SubLineType getType() {
	return type;
    }
    
    public String getText() {
	return text;
    }

}
