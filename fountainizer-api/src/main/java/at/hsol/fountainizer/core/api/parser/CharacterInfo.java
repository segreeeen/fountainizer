package at.hsol.fountainizer.core.api.parser;

import java.util.TreeSet;

public interface CharacterInfo {
    int getGender();

    boolean isEmpty();

    int getAge();

    int getTakes();

    String getName();

    Integer getFirstTake();

    Integer getLastTake();

    TreeSet<? extends Scene> getScenes();
}
