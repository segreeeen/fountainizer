package at.hsol.fountainizer.core.parser;

import at.hsol.fountainizer.core.parser.api.HeadingType;
import at.hsol.fountainizer.core.parser.api.LineType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SceneMetaData {
	private final Map<HeadingType, LinkedList<Scene>> scenes = new HashMap<>();
	private int totalScenes = 0;
	private Scene currentScene;

	public SceneMetaData() {
		this.currentScene = new Scene(0, 0, 0, HeadingType.CUSTOM, new ParserLine(null, 0));
	}

	void inc(ParserLine l) {
		totalScenes++;
		HeadingType type = l.getHeadingType();
		if (scenes.containsKey(type)) {
			LinkedList<Scene> list = scenes.get(type);
			currentScene.setSceneEnd(l.getLineNr() - 1);
			currentScene = new Scene(l.getLineNr(), list.size() + 1, totalScenes, l.getHeadingType(), l);
			list.add(currentScene);
		} else {
			LinkedList<Scene> list = new LinkedList<>();
			currentScene.setSceneEnd(l.getLineNr() - 1);
			currentScene = new Scene(l.getLineNr(), 1, totalScenes, l.getHeadingType(), l);
			list.add(currentScene);
			scenes.put(l.getHeadingType(), list);
		}
	}

	Scene getCurrentScene() {
		return currentScene;
	}

	int getTotalScenes() {
		return totalScenes;
	}

}
