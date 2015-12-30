package at.hacksolutions.f2p.pdfbox;

import java.util.ArrayList;

public class RichFormatParser {

    private ArrayList<RichFormat> formats = new ArrayList<RichFormat>();
    private final String s;

    public RichFormatParser(String s) {
	this.s = s;
    }

    public ArrayList<RichFormat> parse() {
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
	    } else {
		currentString.append(c[i]);
		if (i == c.length-1) {
		    addFormat(currentString.toString(), null);
		}
	    }
	}

	return formats;
    }

    /**
     * @return format at position pos in string s
     */
    private static Format getFormat(String s, int pos) {
	if (s.charAt(pos) == '*') {
	    int count = 0;
	    do {
		count++;
	    } while ((pos + count) < s.length()
		    && s.charAt(pos + count) == '*');

	    if (count == 1) {
		return Format.ITALIC;
	    } else if (count == 2) {
		return Format.BOLD;
	    } else if (count == 3) {
		return Format.BITALIC;
	    }
	} else if (s.charAt(pos) == '_') {
	    return Format.UNDERLINED;
	}
	return null;
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
	} else {
	    newFormat = new RichFormat(false, false, false);
	}

	newFormat.setText(s);
	formats.add(newFormat);
    }

    enum Format {
	BOLD(2), ITALIC(1), BITALIC(3), UNDERLINED(1);

	int val;

	private Format(int val) {
	    this.val = val;
	}
    }
}
