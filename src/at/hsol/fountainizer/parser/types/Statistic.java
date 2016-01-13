package at.hsol.fountainizer.parser.types;

import java.util.List;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.parser.data.Characters;
import at.hsol.fountainizer.parser.data.FCharacter;

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
    private Characters characters = new Characters();

    public Statistic(Characters characters) {
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

    protected void incCharacter(String name) {
	characters.incCharCount(name);
	this.character++;
    }

    protected void incHeading() {
	this.heading++;
    }

    protected void incDialogue() {
	this.dialogue++;
    }

    protected void incParenthetical() {
	this.parenthetical++;
    }

    protected void incTransition() {
	this.transition++;
    }

    protected void incAction() {
	this.action++;
    }

    protected void incLyrics() {
	this.lyrics++;
    }

    protected void incCentered() {
	this.centered++;
    }

    protected void incEmtpy() {
	this.emtpy++;
    }

}
