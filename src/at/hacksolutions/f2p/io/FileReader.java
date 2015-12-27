package at.hacksolutions.f2p.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import at.hacksolutions.f2p.parser.line.GeneralLine;
import at.hacksolutions.f2p.parser.line.Lines;

public class FileReader {
    public static Lines getLines(String fileName) throws IOException {

	ArrayList<GeneralLine> linesList = new ArrayList<>();
	FileInputStream fstream = new FileInputStream(fileName);
	InputStream filein = fstream;
	InputStreamReader reader = new InputStreamReader(filein,
		StandardCharsets.UTF_8);
	BufferedReader readLine = new BufferedReader(reader);
	
	int lineNr = 0;
	// read content lines
	while (readLine.ready()) {
	    String text = readLine.readLine();
	    GeneralLine newLine;
	    if (text.isEmpty()) {
		newLine = new GeneralLine(null, lineNr);
	    } else {
		newLine = new GeneralLine(text, lineNr);
	    }
	    linesList.add(newLine);
	    lineNr++;
	}
	readLine.close();
	GeneralLine[] lineArray = linesList.toArray(new GeneralLine[] {});
	return new Lines(lineArray);
    }
}
