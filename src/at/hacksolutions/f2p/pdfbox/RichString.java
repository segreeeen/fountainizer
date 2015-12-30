package at.hacksolutions.f2p.pdfbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class RichString {

    private ArrayList<RichFormat> formattings;

    public ArrayList<RichFormat> getFormattings() {
	return formattings;
    }

    public RichString(String rawText) {
	this(rawText, null);
    }

    private RichString(ArrayList<RichFormat> formattings) {
	this.formattings = formattings;
    }

    public RichString(String rawText, RichFormat singleFormatting) {
	formattings = new ArrayList<RichFormat>();

	if (singleFormatting == null) {
	    RichFormatParser rfp = new RichFormatParser(rawText);
	    formattings = rfp.parse();
	} else {
	    singleFormatting.setText(rawText);
	    formattings.add(singleFormatting);
	}

    }

    public RichString substring(int beginIndex, int endIndex) {
	ArrayList<RichFormat> formattings = new ArrayList<RichFormat>();

	int i = 0;

	RichFormat currentFormat = this.formattings.get(0);
	String currentString = currentFormat.getText();
	String newString = "";
	while (i < endIndex) {
	    newString = "";
	    if (i + currentString.length() < beginIndex) {
		// do nothing
	    } else if (i + currentString.length() < endIndex) {
		if (i < beginIndex) {
		    newString = (currentString.substring(beginIndex - i));
		} else {
		    newString = (currentString);
		}
		formattings.add(currentFormat.cloneWithNewText(newString));
	    } else if (i + currentString.length() >= endIndex) {
		if (i < beginIndex) {
		    newString = (currentString.substring(beginIndex - i,
			    endIndex - i));
		} else {
		    newString = (currentString.substring(0, endIndex - i));
		}
		formattings.add(currentFormat.cloneWithNewText(newString));
	    }
	    i = i + currentString.length();
	    if (currentFormat != this.formattings.get(formattings.size()-1)) {
		currentFormat = this.formattings
			.get(this.formattings.indexOf(currentFormat) + 1);
		currentString = currentFormat.getText();

	    } else {
		i = endIndex;
	    }

	}

	return new RichString(formattings);
    }

    @Override
    public String toString() {
	String res = "";
	for (RichFormat string : formattings) {
	    res = res + string.getText();
	}
	return res;
    }

    public void convertToUpperCase() {
	for (RichFormat string : this.formattings) {
	    string.setText(string.getText().toUpperCase());
	}
    }

    public float stringWidth(AbstractPager pager) throws IOException {
	float width = 0.0f;
	for (RichFormat text : formattings) {
	    width = width + text.stringWidth(pager);
	}
	return width;
    }

}
