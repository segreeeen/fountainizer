package at.hsol.fountainizer.parser.meta;

import java.util.HashSet;

public class FCharacter {
    /**
     * Gender = female
     */
    public final static int G_FEMALE = 1;
    
    /**
     * Gender = male
     */
    public final static int G_MALE = 2;
    
    /**
     * Gender = unknown
     */
    public final static int G_UNKNOWN = 3;
    
    
    HashSet<String> abbreviations = new HashSet<String>();
    private int takes = 0;
    private String name;
    private int age = G_UNKNOWN;
    private int gender = G_UNKNOWN;
    
    FCharacter(String name) {
	this.name = name;
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
}
