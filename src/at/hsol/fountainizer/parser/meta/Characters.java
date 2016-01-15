package at.hsol.fountainizer.parser.meta;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import at.hsol.fountainizer.Options;

public class Characters {
    private static final String CHARACTER_ASSIGNMENT = "=";
    
    private HashMap<String, FCharacter> charRegister = new HashMap<>();
    
    public void incCharCount(String name) {
	name = name.replaceAll("^", " ");
	name = name.toLowerCase();
	name = name.trim();
	FCharacter n = charRegister.get(name);
	if (n != null) {
	    n.incTakes();
	}
    }
    
    /**
     * Returns a Map of the Characters and the number of their takes.
     */
    public List<FCharacter> getCharacters(Options options) {
	if (charRegister.isEmpty()) {
	    return null;
	}
	
	LinkedList<FCharacter> sortedChars = new LinkedList<>();
	for (Map.Entry<String, FCharacter> e: charRegister.entrySet()) {
	    sortedChars.add(e.getValue());
	}
	if (options.sortCharacters() == Options.SORT_BY_NAME) {
	    Collections.sort(sortedChars, nameCmp);
	    return sortedChars;
	} else if (options.sortCharacters() == Options.SORT_BY_TAKES) {
	    Collections.sort(sortedChars, takeCmp);
	    return sortedChars;
	} else {
	    throw new IllegalArgumentException("\nsortCharacters has to be set.\n "
	    	+ "\n...Seriously, how did you get here?"
	    	+ "\n\nStop screwing around, god damnit. ");
	}
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
	if (charName.contains(CHARACTER_ASSIGNMENT)) {
	    String[] abbs = charName.split(CHARACTER_ASSIGNMENT);
	    charName = abbs[0].trim();
	}
	for (Map.Entry<String, FCharacter> e : charRegister.entrySet()) {
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
	if (charName.split(CHARACTER_ASSIGNMENT).length <= 1) {
	    String charInRegister = lookup(charName);
	    if (charInRegister == null) {
		charRegister.put(charName, new FCharacter(charName));
		return charName;
	    } else {
		return null;
	    }
	} else {
	    String[] abbs = charName.split(CHARACTER_ASSIGNMENT);
	    String fullName = abbs[0].trim();
	    String charInRegister = lookup(fullName);
	    if (charInRegister == null) {
		charRegister.put(fullName, new FCharacter(fullName));
		for (int i = 1; i < abbs.length; i++) {
		    charRegister.get(fullName).abbreviations.add(abbs[i].trim());
		}
		return fullName;
	    } else {
		FCharacter n = charRegister.get(charInRegister);
		for (String s : abbs) {
		    s = s.trim();
		    s = s.toLowerCase();
		    n.abbreviations.add(s);
		}
		return fullName;
	    }
	}
    }
    
    private Comparator<FCharacter> nameCmp = new Comparator<FCharacter>(){
	    @Override
	    public int compare(FCharacter c1, FCharacter c2) {
		String o1 = c1.getName();
		String o2 = c2.getName();
		o1 = o1.toLowerCase();
		o2 = o2.toLowerCase();
		int ret = o1.charAt(0) - o2.charAt(0);
		if (ret == 0) {
		    int lenRet = o1.length()-o2.length();
		    if ( lenRet == 0) {
			for (int i = o1.length(); i<o1.length(); i++) {
			    if (o1.charAt(i) - o2.charAt(i) < 0) {
				return o1.charAt(i) - o2.charAt(i);
			    }
			}
			return 1;
		    } else {
			return lenRet;
		    }
		} else {
		    return ret;
		}
	    }
	};
	
	private Comparator<FCharacter> takeCmp = new Comparator<FCharacter>(){
	    @Override
	    public int compare(FCharacter c1, FCharacter c2) {
		return c2.getTakes() - c1.getTakes();
	    }
	};
	
	
}
