package at.hsol.fountainizer.core.parser.api;

import java.util.List;

public interface Statistics {
    List<? extends CharacterInfo> getCharacterStats();

    int getCharacterLines();

    int getHeading();

    int getDialogue();

    int getParenthetical();

    int getTransition();

    int getAction();

    int getLyrics();

    int getCentered();

    int getEmtpy();

    List<? extends CharacterInfo> getCharacters();

    String lookupCharacter(String text);
}
