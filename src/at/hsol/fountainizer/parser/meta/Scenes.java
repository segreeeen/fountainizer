package at.hsol.fountainizer.parser.meta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.interfaces.MarginType;

public class Scenes {
    private Map<MarginType, LinkedList<Scene>> scenes = new HashMap<>();
    private int totalScenes = 0;
    private Scene currentScene;

    void inc(SimpleLine l) {
	if (scenes.containsKey(l.getLineType())) {
	    LinkedList<Scene> list = scenes.get(l.getLineType());
	    currentScene = new Scene(l.getLineNr(), list.size()+1,l.getLineType());
	    list.add(currentScene);
	} else {
	    LinkedList<Scene> list = new LinkedList<>();
	    currentScene = new Scene(l.getLineNr(), list.size()+1,l.getLineType());
	    list.add(currentScene);
	    scenes.put(l.getLineType(), list);
	}
	totalScenes++;
    }
    
    Scene getCurrentScene() {
	return currentScene;
    }
    
    int getTotalScenes() {
	return totalScenes;
    }
    
    
}
