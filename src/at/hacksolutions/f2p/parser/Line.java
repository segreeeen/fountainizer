package at.hacksolutions.f2p.parser;

public class Line {
    /*
     * Contains the original line of text in the file. May be null if line is
     * empty.
     */
    private final String text;
    
    /*
     * The absolute line number, starts at 0(!)
     */
    private final int lineNr;
    
    /*
     * Contains the Type of the line.
     */
    private LineType type;

    /*
     * True if it is a (dialogue type) dual dialogue
     */
    private boolean dualDialogue;

    /*
     * Contains the line number if lineType is character
     */
    private int takeNumber;

    /*
     * True if the Line starts a comment.
     */
    private boolean commentStart;

    /*
     * True if the Line ends a comment.
     */
    private boolean commentEnd;
    
    public Line(String text, int lineNr) {
	this.text = text;
	this.lineNr = lineNr;
    }
    

    public LineType getLineType() {
	return type;
    }

    public void setLineType(LineType lineType) {
	this.type = lineType;
    }

    public boolean isDualDialogue() {
	return dualDialogue;
    }

    public void setDualDialogue(boolean dualDialogue) {
	this.dualDialogue = dualDialogue;
    }

    public int getTakeNumber() {
	return takeNumber;
    }

    public void setTakeNumber(int takeNumber) {
	this.takeNumber = takeNumber;
    }

    public boolean isCommentStart() {
	return commentStart;
    }

    public void setCommentStart(boolean commentStart) {
	this.commentStart = commentStart;
    }

    public boolean isCommentEnd() {
	return commentEnd;
    }

    public void setCommentEnd(boolean commentEnd) {
	this.commentEnd = commentEnd;
    }

    public String getText() {
	return text;
    }

    public int getLineNr() {
	return lineNr;
    }

}
