package at.hsol.fountainizer.parser.meta;

import at.hsol.fountainizer.parser.content.SimpleLine;
import at.hsol.fountainizer.parser.interfaces.MarginType;

public class Scene implements Comparable<Scene> {
	private final SimpleLine line;
	private final int sceneStart;
	private int sceneEnd = -1;
	private final MarginType type;
	private final int lineTypeNumber;
	private final int totalSceneNr;

	public Scene(int lineNumber, int lineTypeNumber, int totalSceneNr, MarginType marginType, SimpleLine line) {
		this.sceneStart = lineNumber;
		this.type = marginType;
		this.lineTypeNumber = lineTypeNumber;
		this.line = line;
		this.totalSceneNr = totalSceneNr;
	}

	public int getScenestart() {
		return sceneStart;
	}

	public MarginType getType() {
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
		Scene other = (Scene) obj;
		if (sceneStart != other.sceneStart)
			return false;
		return true;
	}

	public int getSceneend() {
		return sceneEnd;
	}

	public void setSceneEnd(int sceneEnd) {
		this.sceneEnd = sceneEnd;
	}

	public SimpleLine getLine() {
		return line;
	}

	@Override
	public int compareTo(Scene o) {
		return this.sceneStart - o.getScenestart();
	}

	public int getTotalSceneNr() {
		return totalSceneNr;
	}

}
