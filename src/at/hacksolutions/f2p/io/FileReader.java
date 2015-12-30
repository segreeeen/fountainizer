package at.hacksolutions.f2p.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import at.hacksolutions.f2p.parser.line.DynamicLines;

public class FileReader {
    public static DynamicLines getLines(String fileName) throws IOException {

	DynamicLines linesList = new DynamicLines();
	FileInputStream fstream = new FileInputStream(fileName);
	InputStream filein = fstream; 
	InputStreamReader reader = new InputStreamReader(filein,
		StandardCharsets.UTF_8);
	BufferedReader readLine = new BufferedReader(reader);

	while (readLine.ready()) {
	    String text = readLine.readLine();
	    linesList.add(text);
	}
	readLine.close();
	return linesList;
    }
}
