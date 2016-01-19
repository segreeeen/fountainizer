package at.hsol.fountainizer.parser.meta;

import at.hsol.fountainizer.parser.interfaces.MarginType;

public class Scene {
    private final int lineNumber;
    private final MarginType type;
    private int lineTypeNumber;
    
    public Scene(int lineNumber, int lineTypeNumber, MarginType marginType) {
	this.lineNumber = lineNumber;
	this.type = marginType;
	this.lineTypeNumber = lineTypeNumber;
    }
    
    public int getLineNumber() {
	return lineNumber;
    }
    
    public MarginType getType() {
	return type;
    }

    public int getLineTypeNumber() {
	return lineTypeNumber;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + lineNumber;
	result = prime * result + lineTypeNumber;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Scene other = (Scene) obj;
	if (lineNumber != other.lineNumber)
	    return false;
	if (lineTypeNumber != other.lineTypeNumber)
	    return false;
	return true;
    }
}
