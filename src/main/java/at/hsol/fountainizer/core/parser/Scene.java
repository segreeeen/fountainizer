package at.hsol.fountainizer.core.parser;

import at.hsol.fountainizer.core.parser.api.HeadingType;

public class Scene implements Comparable<Scene> {
	private final ParserLine parserLine;
	private final int sceneStart;
	private int sceneEnd = -1;
	private final HeadingType type;
	private final int lineTypeNumber;
	private final int totalSceneNr;

	public Scene(int lineNumber, int lineTypeNumber, int totalSceneNr, HeadingType marginType, ParserLine parserLine) {
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
		Scene other = (Scene) obj;

		return sceneStart == other.sceneStart;
	}

	public int getSceneend() {
		return sceneEnd;
	}

	public void setSceneEnd(int sceneEnd) {
		this.sceneEnd = sceneEnd;
	}

	public ParserLine getLine() {
		return parserLine;
	}

	@Override
	public int compareTo(Scene o) {
		return this.sceneStart - o.getScenestart();
	}

	public int getTotalSceneNr() {
		return totalSceneNr;
	}

}
