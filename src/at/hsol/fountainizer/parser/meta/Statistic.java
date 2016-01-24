package at.hsol.fountainizer.parser.meta;

import java.util.List;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.interfaces.MarginType;
import at.hsol.fountainizer.parser.types.HeadingType;
import at.hsol.fountainizer.parser.types.LineType;

/**
 * This class is used to count... Everything.
 * 
 * @author Felix Batusic
 */
public class Statistic {
    private int dialogue = 0;
    private int parenthetical = 0;
    private int transition = 0;
    private int action = 0;
    private int lyrics = 0;
    private int centered = 0;
    private int emtpy = 0;
    private FCharacters characters;
    private Scenes scenes;

    public Statistic(Options options) {
	this.characters = new FCharacters(options);
	this.scenes = new Scenes();
    }

    public List<FCharacter> getCharacterStats(Options options) {
	return characters.getCharacters();
    }

    public int getCharacterLines() {
	return characters.getTotalTakes();
    }

    public int getHeading() {
	return scenes.getTotalScenes();
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

    public void countLine(SimpleLine l) {
	MarginType t = l.getLineType();
	if (t.getClass() == HeadingType.class) {
	    incHeading(l);
	} else if (t == LineType.PARENTHETICAL) {
	    incParenthetical();
	} else if (t == LineType.LYRICS) {
	    incLyrics();
	} else if (t == LineType.CENTERED) {
	    incCentered();
	} else if (t == LineType.TRANSITION) {
	    incTransition();
	} else if (t == LineType.CHARACTER) {
	    incCharacter(l);
	} else if (t == LineType.DIALOGUE) {
	    incDialogue();
	} else if (t == LineType.EMPTY) {
	    incEmtpy();
	} else {
	    incAction();
	}
    }
    
    private void incCharacter(SimpleLine l) {
	this.characters.incCharCount(l, scenes.getCurrentScene());
	l.setLineTypeNumber(characters.getTotalTakes());
    }

    private void incHeading(SimpleLine l) {
	scenes.inc(l);
    }

    private void incDialogue() {
	this.dialogue++;
    }

    private void incParenthetical() {
	this.parenthetical++;
    }

    private void incTransition() {
	this.transition++;
    }

    private void incAction() {
	this.action++;
    }

    private void incLyrics() {
	this.lyrics++;
    }

    private void incCentered() {
	this.centered++;
    }

    private void incEmtpy() {
	this.emtpy++;
    }

    public FCharacters getCharacters() {
	return characters;
    }

}
