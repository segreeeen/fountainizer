package at.hsol.fountainizer.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import at.hsol.fountainizer.parser.line.DynamicLines;
import at.hsol.fountainizer.parser.line.SimpleLine;

/**
 * @author Felix Batusic
 */
public class FileReader {
    public static DynamicLines getLines(String fileName) throws IOException {
	DynamicLines lines = new DynamicLines();
	InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
	BufferedReader readLine = new BufferedReader(reader);
	SimpleLine prev = null;
	SimpleLine cur = null;
	SimpleLine next = null;
	while (readLine.ready()) {
	    String text = readLine.readLine();
	    text = cleanText(text);
	    if (prev == null) { //first line
		prev = lines.add(text);
	    } else if (cur == null) { //second line
		cur = lines.add(text);
		cur.setPrev(prev);
		prev.setNext(cur);
	    } else { //third line
		next = lines.add(text);
		next.setPrev(cur);
		cur.setNext(next);
		prev = cur;
		cur = next;
		next = null;
	    } 
	}
	readLine.close();
	return lines;
    }
    
    private static String cleanText(String text) {
	text = text.replaceAll("[\\t\\n\\r]", "");
	text = text.replaceAll("[\uFFFD]", "");
	text = text.replaceAll("[\uFEFF]", "");
	text = text.replaceAll("[\u0084]", "");
	text = text.trim();
	return text;
    }

}
