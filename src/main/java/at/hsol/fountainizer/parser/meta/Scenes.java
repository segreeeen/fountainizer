package at.hsol.fountainizer.parser.meta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.interfaces.MarginType;
import at.hsol.fountainizer.parser.types.HeadingType;

public class Scenes {
	private Map<MarginType, LinkedList<Scene>> scenes = new HashMap<>();
	private int totalScenes = 0;
	private Scene currentScene;

	public Scenes() {
		this.currentScene = new Scene(0, 0, 0, HeadingType.CUSTOM, new SimpleLine(null, 0));
	}

	void inc(SimpleLine l) {
		totalScenes++;
		if (scenes.containsKey(l.getLineType())) {
			LinkedList<Scene> list = scenes.get(l.getLineType());
			currentScene.setSceneEnd(l.getLineNr() - 1);
			currentScene = new Scene(l.getLineNr(), list.size() + 1, totalScenes, l.getLineType(), l);
			list.add(currentScene);
		} else {
			LinkedList<Scene> list = new LinkedList<>();
			currentScene.setSceneEnd(l.getLineNr() - 1);
			currentScene = new Scene(l.getLineNr(), list.size() + 1, totalScenes, l.getLineType(), l);
			list.add(currentScene);
			scenes.put(l.getLineType(), list);
		}
	}

	Scene getCurrentScene() {
		return currentScene;
	}

	int getTotalScenes() {
		return totalScenes;
	}

}
