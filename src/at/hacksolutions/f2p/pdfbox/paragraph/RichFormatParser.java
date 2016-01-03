package at.hacksolutions.f2p.pdfbox.paragraph;

import java.util.LinkedList;

/**
 * @author Felix Batusic
 */
public class RichFormatParser {

    private LinkedList<RichFormat> formats = new LinkedList<RichFormat>();
    private final String s;

    public RichFormatParser(String s) {
	this.s = s;
    }

    public LinkedList<RichFormat> parse() {
	char[] c = s.toCharArray();
	StringBuffer currentString = new StringBuffer();
	Format currentFormat = null;
	Format subFormat = null;
	if (!s.contains("*") && !s.contains("_")) {
	    addFormat(s, null);
	    return formats;
	}

	for (int i = 0; i < c.length; i++) {
	    char curChar = c[i];
	    if (curChar == '*') {
		Format f = getFormat(s, i);
		i += (f.val - 1);
		if (currentFormat == null) {
		    addFormat(currentString.toString(), null);
		    currentString = new StringBuffer();
		    currentFormat = f;
		} else if (currentFormat == f) {
		    addFormat(currentString.toString(), f);
		    currentString = new StringBuffer();
		    currentFormat = null;
		} else if ((currentFormat == Format.BOLD
			|| currentFormat == Format.ITALIC)
			&& subFormat == null) {
		    if (f == Format.ITALIC) {
			addFormat(currentString.toString(), Format.BOLD);
		    } else if (f == Format.BOLD) {
			addFormat(currentString.toString(), Format.ITALIC);
		    }
		    subFormat = f;
		    currentString = new StringBuffer();
		} else if ((currentFormat == Format.BOLD
			|| currentFormat == Format.ITALIC)
			&& subFormat != null) {
		    addFormat(currentString.toString(), Format.BITALIC);
		    subFormat = null;
		    currentString = new StringBuffer();
		} else {
		    addFormat(currentString.toString(), null);
		    currentString = new StringBuffer();
		    currentFormat = f;
		    subFormat = null;
		}
	    } else if (curChar == '_') {
		Format f = getFormat(s, i);
		i += (f.val - 1);
		if (currentFormat == null) {
		    addFormat(currentString.toString(), null);
		    currentString = new StringBuffer();
		    currentFormat = f;
		} else if (currentFormat == f) {
		    addFormat(currentString.toString(), f);
		    currentString = new StringBuffer();
		    currentFormat = null;
		} else {
		    addFormat(currentString.toString(), null);
		    currentString = new StringBuffer();
		    currentFormat = f;
		    subFormat = null;
		}
	    } else if (curChar == '/') {
		Format f = getFormat(s, i);
		i += (f.val - 1);
		if (currentFormat == null) {
		    addFormat(currentString.toString(), null);
		    currentString = new StringBuffer();
		    currentFormat = f;
		} else if (currentFormat == f) {
		    addFormat(currentString.toString(), f);
		    currentString = new StringBuffer();
		    currentFormat = null;
		} else {
		    addFormat(currentString.toString(), null);
		    currentString = new StringBuffer();
		    currentFormat = f;
		    subFormat = null;
		}
	    } else {
		currentString.append(c[i]);
		if (i == c.length - 1) {
		    addFormat(currentString.toString(), null);
		}
	    }
	}

	return formats;
    }

    private Format getFormat(String s, int pos) {
	if (s.charAt(pos) == '*') {
	    int astCount = getDoubleFormat(s, pos, '*');
	    if (astCount == 1) {
		int slashCount = getDoubleFormat(s, pos, '/');
		if (slashCount == 1) {
		    return Format.COMMENT;
		} else {
		    return Format.ITALIC;
		}
	    } else if (astCount == 2) {
		return Format.BOLD;
	    } else if (astCount == 3) {
		return Format.BITALIC;
	    }

	} else if (s.charAt(pos) == '_') {
	    return Format.UNDERLINED;
	} else if (s.charAt(pos) == '/') {
	    int slashCount = getDoubleFormat(s, pos, '*');
	    if (slashCount == 1) {
		return Format.COMMENT;
	    }
	}
	return null;
    }

    private int getDoubleFormat(String s, int pos, char followingChar) {
	int count = 0;
	if (s.charAt(pos) == followingChar) {
	    do {
		count++;
	    } while ((pos + count) < s.length()
		    && s.charAt(pos + count) == followingChar);
	} else {
	    while (((pos + 1) + count) < s.length()
		    && s.charAt((pos + 1) + count) == followingChar) {
		count++;
	    } 
	}
	return count;

    }

    private void addFormat(String s, Format f) {
	RichFormat newFormat;
	if (f == Format.BOLD) {
	    newFormat = new RichFormat(false, true, false);
	} else if (f == Format.ITALIC) {
	    newFormat = new RichFormat(false, false, true);
	} else if (f == Format.BITALIC) {
	    newFormat = new RichFormat(false, true, true);
	} else if (f == Format.UNDERLINED) {
	    newFormat = new RichFormat(true, false, false);
	} else if (f == Format.COMMENT) {
	    newFormat = null;
	} else {
	    newFormat = new RichFormat(false, false, false);
	}

	if (newFormat != null) {
	    newFormat.setText(s);
	    formats.add(newFormat);
	}
    }

    enum Format {
	BOLD(2), ITALIC(1), BITALIC(3), UNDERLINED(1), COMMENT(2);

	int val;

	private Format(int val) {
	    this.val = val;
	}
    }
}
