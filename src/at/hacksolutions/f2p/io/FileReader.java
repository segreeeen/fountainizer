package at.hacksolutions.f2p.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import at.hacksolutions.f2p.parser.line.SimpleLine;
import at.hacksolutions.f2p.parser.line.ParserLines;
import at.hacksolutions.f2p.parser.line.FixedLines;

public class FileReader {
    public static FixedLines getLines(String fileName) throws IOException {

	ArrayList<SimpleLine> linesList = new ArrayList<>();
	FileInputStream fstream = new FileInputStream(fileName);
	InputStream filein = fstream; 
	InputStreamReader reader = new InputStreamReader(filein,
		StandardCharsets.UTF_8);
	BufferedReader readLine = new BufferedReader(reader);
	
	int lineNr = 0;
	// read content lines
	while (readLine.ready()) {
	    String text = readLine.readLine();
	    SimpleLine newLine;
	    if (text.isEmpty()) {
		newLine = new SimpleLine(null, lineNr);
	    } else {
		newLine = new SimpleLine(text, lineNr);
	    }
	    linesList.add(newLine);
	    lineNr++;
	}
	readLine.close();
	SimpleLine[] lineArray = linesList.toArray(new SimpleLine[] {});
	return new FixedLines(lineArray);
    }
}
