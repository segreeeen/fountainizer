package at.hsol.fountainizer.core.parser;

import at.hsol.fountainizer.core.parser.api.CharacterInfo;

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
	private final TreeSet<Scene> scenes;

	CharacterDescription(String name) {
		this.name = name;
		this.scenes = new TreeSet<>();
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
	public TreeSet<Scene> getScenes() {
		return scenes;
	}

	void setGender(int gender) {
		this.gender = gender;
	}

	void setAge(int age) {
		this.age = age;
	}

	void addScene(Scene scene) {
		scenes.add(scene);
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

