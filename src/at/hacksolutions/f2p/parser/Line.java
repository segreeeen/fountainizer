package at.hacksolutions.f2p.parser;

import java.util.ArrayList;

public class Line {
    /*
     * Contains the original line of text in the file. May be null if line is empty.
     */
    private final String originalText;
    
    /*
     * Contains the final line of the text as a list of SubLine elements.
     */
    private ArrayList<SubLine> finalText;
    
    
    /*
     * Contains the Type of the line.
     */
    private LineType type;
    
    /*
     * Indicating that the next line is empty.
     */
    private boolean nextEmpty;
    
    /*
     * Indicating that the previous line is empty.
     */
    private boolean prevEmpty;
    
    /*
     * True if it is a dual dialogue
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
    
    
    
    public ArrayList<SubLine> getFinalText() {
        return finalText;
    }



    public void setFinalText(ArrayList<SubLine> finalText) {
        this.finalText = finalText;
    }



    public boolean isNextEmpty() {
        return nextEmpty;
    }



    public void setNextEmpty(boolean nextEmpty) {
        this.nextEmpty = nextEmpty;
    }



    public boolean isPrevEmpty() {
        return prevEmpty;
    }



    public void setPrevEmpty(boolean prevEmpty) {
        this.prevEmpty = prevEmpty;
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



    public String getOriginalText() {
        return originalText;
    }



    public Line(String originalText) {
	this.originalText = originalText;
    }
    
}
