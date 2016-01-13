package at.hsol.fountainizer.parser.data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import at.hsol.fountainizer.parser.types.ParserConstants;

public class Characters {
    private HashMap<String, CharRegisterNode> charRegister = new HashMap<>();
    
    public void incCharCount(String name) {
	name = name.replaceAll("^", " ");
	name = name.toLowerCase();
	name = name.trim();
	CharRegisterNode n = charRegister.get(name);
	if (n != null) {
	    n.incTakes();
	}
    }
    
    /**
     * Returns a Map of the Characters and the number of their takes.
     */
    public TreeMap<String, Integer> getCharacters() {
	if (charRegister.isEmpty()) {
	    return null;
	}
	
	TreeMap<String, Integer> characters = new TreeMap<>(new Comparator<String>(){
	    @Override
	    public int compare(String o1, String o2) {
		o1 = o1.toLowerCase();
		o2 = o2.toLowerCase();
		int ret = o1.charAt(0) - o2.charAt(0);
		if (ret == 0) {
		    return 1;
		} else {
		    return ret;
		}
	    }
	});
	for (Map.Entry<String, CharRegisterNode> e : charRegister.entrySet()) {
	    characters.put(e.getKey(), e.getValue().getTakes());
	}
	return characters;
    }
    
    /**
     * Returns the corresponding Charactername
     * 
     * @param charName
     *            charname or abbreviated charname
     * @return full character name
     */
    public String lookup(String charName) {
	charName = charName.replaceAll("^", " ");
	charName = charName.trim();
	charName = charName.toLowerCase();
	if (charName.contains(ParserConstants.CHARACTER_ASSIGNMENT)) {
	    String[] abbs = charName.split(ParserConstants.CHARACTER_ASSIGNMENT);
	    charName = abbs[0].trim();
	}
	for (Map.Entry<String, CharRegisterNode> e : charRegister.entrySet()) {
	    if (e.getKey().equals(charName)) {
		return e.getKey();
	    } else if (!e.getValue().isEmpty()) {
		if (e.getValue().abbreviations.contains(charName)) {
		    return e.getKey();
		}
	    }
	}
	return null;
    }

    /**
     * Add a character to the character register
     * 
     * @param charName
     *            can be character name or charactername with abbreviaton in the
     *            format "name = abbreviation"
     * @return true if added, else false
     */
    public String add(String charName) {
	charName = charName.replaceAll("^", " ");
	charName = charName.trim();
	charName = charName.toLowerCase();
	if (charName.split(ParserConstants.CHARACTER_ASSIGNMENT).length <= 1) {
	    String charInRegister = lookup(charName);
	    if (charInRegister == null) {
		charRegister.put(charName, new CharRegisterNode());
		return charName;
	    } else {
		return null;
	    }
	} else {
	    String[] abbs = charName.split(ParserConstants.CHARACTER_ASSIGNMENT);
	    String fullName = abbs[0].trim();
	    String charInRegister = lookup(fullName);
	    if (charInRegister == null) {
		charRegister.put(fullName, new CharRegisterNode());
		for (int i = 1; i < abbs.length; i++) {
		    charRegister.get(fullName).abbreviations.add(abbs[i].trim());
		}
		return fullName;
	    } else {
		CharRegisterNode n = charRegister.get(charInRegister);
		for (String s : abbs) {
		    s = s.trim();
		    s = s.toLowerCase();
		    n.abbreviations.add(s);
		}
		return fullName;
	    }
	}
    }

    private class CharRegisterNode {
	protected HashSet<String> abbreviations = new HashSet<String>();
	private int takes = 0;

	protected boolean isEmpty() {
	    return abbreviations.isEmpty();
	}
	
	protected void incTakes() {
	    takes++;
	}
	
	protected int getTakes() {
	    return takes;
	}
	
    }
}
