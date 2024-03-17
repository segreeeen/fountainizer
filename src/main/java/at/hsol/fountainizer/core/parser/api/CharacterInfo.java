package at.hsol.fountainizer.core.parser.api;

import at.hsol.fountainizer.core.parser.Scene;

import java.util.TreeSet;

public interface CharacterInfo {
    int getGender();

    boolean isEmpty();

    int getAge();

    int getTakes();

    String getName();

    Integer getFirstTake();

    Integer getLastTake();

    TreeSet<Scene> getScenes();
}
