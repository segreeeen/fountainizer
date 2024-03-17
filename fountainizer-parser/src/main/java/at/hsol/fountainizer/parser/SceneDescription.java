package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.parser.HeadingType;
import at.hsol.fountainizer.core.api.parser.Scene;

public class SceneDescription implements Comparable<SceneDescription>, Scene {
	private final ParserLine parserLine;
	private final int sceneStart;
	private int sceneEnd = -1;
	private final HeadingType type;
	private final int lineTypeNumber;
	private final int totalSceneNr;

	public SceneDescription(int lineNumber, int lineTypeNumber, int totalSceneNr, HeadingType marginType, ParserLine parserLine) {
		this.sceneStart = lineNumber;
		this.type = marginType;
		this.lineTypeNumber = lineTypeNumber;
		this.parserLine = parserLine;
		this.totalSceneNr = totalSceneNr;
	}

	public int getScenestart() {
		return sceneStart;
	}

	public HeadingType getType() {
		return type;
	}

	public int getLineTypeNumber() {
		return lineTypeNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sceneStart;
		result = prime * result + lineTypeNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SceneDescription other = (SceneDescription) obj;

		return sceneStart == other.sceneStart;
	}

	public int getSceneEnd() {
		return sceneEnd;
	}

	public void setSceneEnd(int sceneEnd) {
		this.sceneEnd = sceneEnd;
	}

	public ParserLine getLine() {
		return parserLine;
	}

	@Override
	public int compareTo(SceneDescription o) {
		return this.sceneStart - o.getScenestart();
	}

	public int getTotalSceneNr() {
		return totalSceneNr;
	}

}
