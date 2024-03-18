package at.hsol.fountainizer.parser;

import at.hsol.fountainizer.core.api.parser.CharacterInfo;
import at.hsol.fountainizer.core.api.parser.Scene;

import java.util.HashSet;
import java.util.TreeSet;

class CharacterDescription implements CharacterInfo {

	public final static int G_FEMALE = 1;
	public final static int G_MALE = 2;
	public final static int G_UNKNOWN = 3;

	private final HashSet<String> abbreviations = new HashSet<>();
	private int takes = 1;
	private String name;
	private int age = G_UNKNOWN;
	private int gender = G_UNKNOWN;
	private Integer firstTake = null;
	private Integer lastTake = null;
	private final TreeSet<SceneDescription> sceneDescriptions;

	CharacterDescription(String name) {
		this.name = name;
		this.sceneDescriptions = new TreeSet<>();
	}

	@Override
	public int getGender() {
		return gender;
	}

	@Override
	public boolean isEmpty() {
		return abbreviations.isEmpty();
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public int getTakes() {
		return takes;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getFirstTake() {
		return firstTake;
	}

	@Override
	public Integer getLastTake() {
		return lastTake;
	}

	@Override
	public TreeSet<? extends Scene> getScenes() {
		return (TreeSet<? extends Scene>) sceneDescriptions;
	}

	void setGender(int gender) {
		this.gender = gender;
	}

	void setAge(int age) {
		this.age = age;
	}

	void addScene(SceneDescription sceneDescription) {
		sceneDescriptions.add(sceneDescription);
	}

	void setName(String name) {
		this.name = name;
	}

	void incTakes() {
		takes++;
	}

	void setLastTake(Integer lastTake) {
		this.lastTake = lastTake;
	}

	void setFirstTake(Integer firstTake) {
		this.firstTake = firstTake;
	}

	public HashSet<String> getAbbreviations() {
		return this.abbreviations;
	}
}

