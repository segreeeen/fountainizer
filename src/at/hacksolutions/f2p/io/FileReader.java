package at.hacksolutions.f2p.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import at.hacksolutions.f2p.parser.line.Line;
import at.hacksolutions.f2p.parser.line.Lines;

public class FileReader {
    public static Lines getLines(String fileName) throws IOException {

	ArrayList<Line> linesList = new ArrayList<>();
	FileInputStream fstream = new FileInputStream(fileName);
	InputStream filein = fstream;
	InputStreamReader reader = new InputStreamReader(filein,
		StandardCharsets.UTF_8);
	BufferedReader readLine = new BufferedReader(reader);
	
	int lineNr = 0;
	// read content lines
	while (readLine.ready()) {
	    String text = readLine.readLine();
	    Line newLine;
	    if (text.isEmpty()) {
		newLine = new Line(null, lineNr);
	    } else {
		newLine = new Line(text, lineNr);
	    }
	    linesList.add(newLine);
	    lineNr++;
	}
	readLine.close();
	Line[] lineArray = linesList.toArray(new Line[] {});
	return new Lines(lineArray);
    }
}
