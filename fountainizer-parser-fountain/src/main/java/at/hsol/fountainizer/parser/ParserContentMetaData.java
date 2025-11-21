package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.CharacterInfo;
import at.hsol.fountainizer.core.api.parser.Statistics;
import at.hsol.fountainizer.core.api.types.LineType;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public List<? extends CharacterInfo> getCharacterStats() {
        return (List)getCharacters();
    }

    public int getCharacterLines() {
        return getTotalTakes();
    }

    public int getHeading() {
        return this.sceneMetaData.getTotalScenes();
    }

    public int getDialogue() {
        return this.dialogue;
    }

    public int getParenthetical() {
        return this.parenthetical;
    }

    public int getTransition() {
        return this.transition;
    }

    public int getAction() {
        return this.action;
    }

    public int getLyrics() {
        return this.lyrics;
    }

    public int getCentered() {
        return this.centered;
    }

    public int getEmtpy() {
        return this.emtpy;
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
        incCharCount(l, this.sceneMetaData.getCurrentScene());
    }

    private void incHeading(ParserLine l) {
        this.sceneMetaData.inc(l);
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
        CharacterDescription n = this.charRegister.get(s);
        if (n != null) {
            if (n.getFirstTake() == null)
                n.setFirstTake(Integer.valueOf(l.getLineNr()));
            n.setLastTake(Integer.valueOf(l.getLineNr()));
            n.addScene(currentSceneDescription);
            n.incTakes();
            this.totalTakes++;
        }
    }

    public List<CharacterDescription> getCharacters() {
        if (this.charRegister.isEmpty())
            return null;
        LinkedList<CharacterDescription> sortedChars = new LinkedList<>();
        for (Map.Entry<String, CharacterDescription> e : this.charRegister.entrySet())
            sortedChars.add(e.getValue());
        if (this.options.sortCharacters() == Options.SortMode.BY_NAME) {
            sortedChars.sort(Comparator.comparing(CharacterDescription::getName));
            return sortedChars;
        }
        if (this.options.sortCharacters() == Options.SortMode.BY_TAKE_COUNT) {
            sortedChars.sort(Comparator.comparing(CharacterDescription::getTakes));
            return sortedChars;
        }
        throw new IllegalArgumentException("\nsortCharacters has to be set.\n \n...Seriously, how did you get here?\n\nStop screwing around, god damnit. ");
    }

    public String resolveCharacterAbbreviation(String charName) {
        final String charNameFormatted = format(charName);

        String abbreviation = charNameFormatted.contains("=")
                ? charNameFormatted.split("=")[0].trim()
                : null;

        //name is not an abbreviation
        if (abbreviation == null && this.charRegister.containsKey(charNameFormatted)) return charNameFormatted;

        //name is an abbreviation

        String resolvedName = this.charRegister.values().stream()
                .filter(desc -> desc.getAbbreviations().contains(abbreviation))
                .findFirst()
                .map(CharacterDescription::getName).orElse(null);

        return resolvedName;
    }

    void add(ParserLine l) {
        String charName = format(l.getText());
        if ((charName.split("=")).length <= 1) {
            String charInRegister = resolveCharacterAbbreviation(charName);
            if (charInRegister == null)
                this.charRegister.put(charName, new CharacterDescription(charName));
        } else {
            String[] abbs = charName.split("=");
            String fullName = abbs[0].trim();
            String charInRegister = resolveCharacterAbbreviation(fullName);
            if (charInRegister == null) {
                this.charRegister.put(fullName, new CharacterDescription(fullName));
                for (int i = 1; i < abbs.length; i++)
                    ((CharacterDescription)this.charRegister.get(fullName)).getAbbreviations().add(abbs[i].trim());
            } else {
                CharacterDescription n = this.charRegister.get(charInRegister);
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
        if (parPos > -1)
            charName = charName.substring(0, parPos);
        charName = charName.trim();
        charName = charName.toLowerCase();
        return charName;
    }

    int getTotalTakes() {
        return this.totalTakes;
    }
}
