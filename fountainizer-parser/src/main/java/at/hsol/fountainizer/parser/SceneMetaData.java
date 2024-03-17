package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.parser.HeadingType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SceneMetaData {
	private final Map<HeadingType, LinkedList<SceneDescription>> scenes = new HashMap<>();
	private int totalScenes = 0;
	private SceneDescription currentSceneDescription;

	public SceneMetaData() {
		this.currentSceneDescription = new SceneDescription(0, 0, 0, HeadingType.CUSTOM, new ParserLine(null, 0));
	}

	void inc(ParserLine l) {
		totalScenes++;
		HeadingType type = l.getHeadingType();
		if (scenes.containsKey(type)) {
			LinkedList<SceneDescription> list = scenes.get(type);
			currentSceneDescription.setSceneEnd(l.getLineNr() - 1);
			currentSceneDescription = new SceneDescription(l.getLineNr(), list.size() + 1, totalScenes, l.getHeadingType(), l);
			list.add(currentSceneDescription);
		} else {
			LinkedList<SceneDescription> list = new LinkedList<>();
			currentSceneDescription.setSceneEnd(l.getLineNr() - 1);
			currentSceneDescription = new SceneDescription(l.getLineNr(), 1, totalScenes, l.getHeadingType(), l);
			list.add(currentSceneDescription);
			scenes.put(l.getHeadingType(), list);
		}
	}

	SceneDescription getCurrentScene() {
		return currentSceneDescription;
	}

	int getTotalScenes() {
		return totalScenes;
	}

}
