package at.hsol.fountainizer.parser.meta;

import java.util.HashSet;
import java.util.TreeSet;

public class CharacterDesc {

	public final static int G_FEMALE = 1;
	public final static int G_MALE = 2;
	public final static int G_UNKNOWN = 3;

	HashSet<String> abbreviations = new HashSet<>();
	private int takes = 1;
	private String name;
	private int age = G_UNKNOWN;
	private int gender = G_UNKNOWN;
	private Integer firstTake = null;
	private Integer lastTake = null;
	private final TreeSet<Scene> scenes;

	CharacterDesc(String name) {
		this.name = name;
		this.scenes = new TreeSet<>();
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	boolean isEmpty() {
		return abbreviations.isEmpty();
	}

	void incTakes() {
		takes++;
	}

	void addScene(Scene scene) {
		scenes.add(scene);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getTakes() {
		return takes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFirstTake() {
		return firstTake;
	}

	void setFirstTake(Integer firstTake) {
		this.firstTake = firstTake;
	}

	public Integer getLastTake() {
		return lastTake;
	}

	void setLastTake(Integer lastTake) {
		this.lastTake = lastTake;
	}

	public TreeSet<Scene> getScenes() {
		return scenes;
	}

}