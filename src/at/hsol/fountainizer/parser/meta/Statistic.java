package at.hsol.fountainizer.parser.meta;

import java.util.List;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.interfaces.MarginType;
import at.hsol.fountainizer.parser.types.LineType;

/**
 * This class is used to count... Everything.
 * 
 * @author Felix Batusic
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
    private FCharacters characters = new FCharacters();

    public Statistic(FCharacters characters) {
	this.characters = characters;
    }

    public List<FCharacter> getCharacterStats(Options options) {
	return characters.getCharacters(options);
    }

    public int getCharacterLines() {
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

    public void countLine(SimpleLine l) {
	MarginType t = l.getLineType();
	if (t == LineType.HEADING) {
	    incHeading();
	} else if (t == LineType.PARENTHETICAL) {
	    incParenthetical();
	} else if (t == LineType.LYRICS) {
	    incLyrics();
	} else if (t == LineType.CENTERED) {
	    incCentered();
	} else if (t == LineType.TRANSITION) {
	    incTransition();
	} else if (t == LineType.CHARACTER) {
	    incCharacter(l.getText());
	} else if (t == LineType.DIALOGUE) {
	    incDialogue();
	} else if (t == LineType.EMPTY) {
	    incEmtpy();
	} else {
	    incAction();
	}
    }

    private void incCharacter(String name) {
	this.characters.add(name);
	this.characters.incCharCount(name);
	this.character++;
	System.out.println(character);
    }

    private void incHeading() {
	this.heading++;
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

}
