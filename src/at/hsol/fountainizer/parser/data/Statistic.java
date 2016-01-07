package at.hsol.fountainizer.parser.data;

/**
 * This class is used to count... Everything. 
 * @author Felix Batusic
 *
 */
public class Statistic {
    private int character = 0;
    private int heading = 0;
    private int dialogue = 0;
    private int parenthetical = 0;
    private int transition = 0;
    private int action = 0;
    private int lyrics = 0;
    private int centered = 0;
    private int emtpy = 0;
    
    public void incCharacter() {
        this.character++;
    }
    public void incHeading() {
        this.heading++;
    }
    public void incDialogue() {
        this.dialogue++;
    }
    public void incParenthetical() {
        this.parenthetical++;
    }
    public void incTransition() {
        this.transition++;
    }
    public void incAction() {
        this.action++;
    }
    public void incLyrics() {
        this.lyrics++;
    }
    public void incCentered() {
        this.centered++;
    }
    public void incEmtpy() {
        this.emtpy++;
    }
    public int getCharacter() {
        return character;
    }
    public int getHeading() {
        return heading;
    }
    public int getDialogue() {
        return dialogue;
    }
    public int getParenthetical() {
        return parenthetical;
    }
    public int getTransition() {
        return transition;
    }
    public int getAction() {
        return action;
    }
    public int getLyrics() {
        return lyrics;
    }
    public int getCentered() {
        return centered;
    }
    public int getEmtpy() {
        return emtpy;
    }
    
    
}
