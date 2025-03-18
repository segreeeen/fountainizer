package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.types.HeadingType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SceneMetaData {
	private final Map<HeadingType, LinkedList<SceneDescription>> scenes = new HashMap<>();

	private int totalScenes = 0;

	private SceneDescription currentSceneDescription;

	public SceneMetaData() {
		this.currentSceneDescription = new SceneDescription(0, 0, 0, HeadingType.CUSTOM, new ParserLine(null));
	}

	void inc(ParserLine l) {
		this.totalScenes++;
		HeadingType type = l.getHeadingType();
		if (this.scenes.containsKey(type)) {
			LinkedList<SceneDescription> list = this.scenes.get(type);
			this.currentSceneDescription.setSceneEnd(l.getLineNr() - 1);
			this.currentSceneDescription = new SceneDescription(l.getLineNr(), list.size() + 1, this.totalScenes, l.getHeadingType(), l);
			list.add(this.currentSceneDescription);
		} else {
			LinkedList<SceneDescription> list = new LinkedList<>();
			this.currentSceneDescription.setSceneEnd(l.getLineNr() - 1);
			this.currentSceneDescription = new SceneDescription(l.getLineNr(), 1, this.totalScenes, l.getHeadingType(), l);
			list.add(this.currentSceneDescription);
			this.scenes.put(l.getHeadingType(), list);
		}
	}

	SceneDescription getCurrentScene() {
		return this.currentSceneDescription;
	}

	int getTotalScenes() {
		return this.totalScenes;
	}
}
