package at.hsol.fountainizer.parser.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import at.hsol.fountainizer.parser.types.ParserConstants;

public class Characters {
    private HashMap<String, CharRegisterNode> charRegister = new HashMap<>();

    /**
     * Returns the corresponding Charactername
     * 
     * @param charName
     *            charname or abbreviated charname
     * @return full character name
     */
    public String lookup(String charName) {
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
	HashSet<String> abbreviations = new HashSet<String>();

	boolean isEmpty() {
	    return abbreviations.isEmpty();
	}
    }
}
