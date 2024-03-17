package at.hsol.fountainizer.parser;

import java.util.*;

import at.hsol.fountainizer.core.api.parser.CharacterInfo;
import at.hsol.fountainizer.core.api.parser.LineType;
import at.hsol.fountainizer.core.api.parser.Options;
import at.hsol.fountainizer.core.api.parser.Statistics;

/**
 * This class is used to count... Everything.
 *
 * @author Felix Batusic
 */
class ParserContentMetaData implements Statistics {
    private int dialogue = 0;
    private int parenthetical = 0;
    private int transition = 0;
    private int action = 0;
    private int lyrics = 0;
    private int centered = 0;
    private int emtpy = 0;
    private final SceneMetaData sceneMetaData;
    private Options options;

    private static final String CHARACTER_ASSIGNMENT = "=";

    private final HashMap<String, CharacterDescription> charRegister = new HashMap<>();

    private int totalTakes = 0;

    ParserContentMetaData(Options options) {
        this.sceneMetaData = new SceneMetaData();
        this.options = options;
    }

    @Override
    public List<? extends CharacterInfo> getCharacterStats() {
        return getCharacters();
    }

    @Override
    public int getCharacterLines() {
        return getTotalTakes();
    }

    @Override
    public int getHeading() {
        return sceneMetaData.getTotalScenes();
    }

    @Override
    public int getDialogue() {
        return dialogue;
    }

    @Override
    public int getParenthetical() {
        return parenthetical;
    }

    @Override
    public int getTransition() {
        return transition;
    }

    @Override
    public int getAction() {
        return action;
    }

    @Override
    public int getLyrics() {
        return lyrics;
    }

    @Override
    public int getCentered() {
        return centered;
    }

    @Override
    public int getEmtpy() {
        return emtpy;
    }

    public void countLine(ParserLine l) {
        LineType t = l.getLineType();
        if (t == LineType.HEADING) {
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

    private void incCharacter(ParserLine l) {
        incCharCount(l, sceneMetaData.getCurrentScene());
    }

    private void incHeading(ParserLine l) {
        sceneMetaData.inc(l);
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

    void setOptions(Options options) {
        this.options = options;
    }


    void incCharCount(ParserLine l, SceneDescription currentSceneDescription) {
        add(l);
        String s = format(l.getText());
        CharacterDescription n = charRegister.get(s);
        if (n != null) {
            if (n.getFirstTake() == null) {
                n.setFirstTake(l.getLineNr());
            }
            n.setLastTake(l.getLineNr());
            n.addScene(currentSceneDescription);
            n.incTakes();
            totalTakes++;
        }
    }

    /**
     * Returns a Map of the Characters and the number of their takes.
     */
    @Override
    public List<CharacterDescription> getCharacters() {
        if (charRegister.isEmpty()) {
            return null;
        }

        LinkedList<CharacterDescription> sortedChars = new LinkedList<>();
        for (Map.Entry<String, CharacterDescription> e : charRegister.entrySet()) {
            sortedChars.add(e.getValue());
        }
        if (options.sortCharacters() == Options.SortMode.BY_NAME) {
            sortedChars.sort(Comparator.comparing(CharacterDescription::getName));
            return sortedChars;
        } else if (options.sortCharacters() == Options.SortMode.BY_TAKE_COUNT) {
            sortedChars.sort(Comparator.comparing(CharacterDescription::getTakes));
            return sortedChars;
        } else {
            throw new IllegalArgumentException("\nsortCharacters has to be set.\n "
                    + "\n...Seriously, how did you get here?" + "\n\nStop screwing around, god damnit. ");
        }
    }

    /**
     * Returns the corresponding Charactername
     *
     * @param charName charname or abbreviated charname
     * @return full character name
     */
    @Override
    public String lookupCharacter(String charName) {
        charName = format(charName);
        if (charName.contains(CHARACTER_ASSIGNMENT)) {
            String[] abbs = charName.split(CHARACTER_ASSIGNMENT);
            charName = abbs[0].trim();
        }
        for (Map.Entry<String, CharacterDescription> e : charRegister.entrySet()) {
            if (e.getKey().equals(charName)) {
                return e.getKey();
            } else if (!e.getValue().isEmpty()) {
                if (e.getValue().getAbbreviations().contains(charName)) {
                    return e.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Add a character to the character register
     *
     * @return true if added, else false
     */
    void add(ParserLine l) {
        String charName = format(l.getText());
        if (charName.split(CHARACTER_ASSIGNMENT).length <= 1) {
            String charInRegister = lookupCharacter(charName);
            if (charInRegister == null) {
                charRegister.put(charName, new CharacterDescription(charName));
            }
        } else {
            String[] abbs = charName.split(CHARACTER_ASSIGNMENT);
            String fullName = abbs[0].trim();
            String charInRegister = lookupCharacter(fullName);
            if (charInRegister == null) {
                charRegister.put(fullName, new CharacterDescription(fullName));
                for (int i = 1; i < abbs.length; i++) {
                    charRegister.get(fullName).getAbbreviations().add(abbs[i].trim());
                }
            } else {
                CharacterDescription n = charRegister.get(charInRegister);
                for (String s : abbs) {
                    s = s.trim();
                    s = s.toLowerCase();
                    n.getAbbreviations().add(s);
                }
            }
        }
    }

    private String format(String charName) {
        charName = charName.replaceAll("\\^", "");
        int parPos = charName.indexOf("(");
        if (parPos > -1) {
            charName = charName.substring(0, parPos);
        }
        charName = charName.trim();
        charName = charName.toLowerCase();
        return charName;
    }

    int getTotalTakes() {
        return totalTakes;
    }
}
