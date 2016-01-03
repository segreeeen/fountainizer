package at.hacksolutions.f2p.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.function.Consumer;

import at.hacksolutions.f2p.parser.line.DynamicLines;

public class FileReader {
    public static DynamicLines getLines(String fileName, LinkedList<Consumer<String>> handlers) throws IOException {

	DynamicLines linesList = new DynamicLines();
	InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8);
	BufferedReader readLine = new BufferedReader(reader);
	CharsetEncoder enc = StandardCharsets.UTF_8.newEncoder();
	while (readLine.ready()) {
	    String text = readLine.readLine();
	    if (handlers != null && !handlers.isEmpty()) {
		callReadHandlers(text, handlers);
	    }
	    text = text.replaceAll("[\\t\\n\\r]", "");
	    text = text.replaceAll("[\uFFFD]", "ae?");
	    text = text.replaceAll("[\uFEFF]", "ue?");
	    text = text.replaceAll("[\u0084]", "oe?");
	    if (text.trim().isEmpty()) {
		linesList.add(null);
	    } else {
		linesList.add(text.trim());
	    }
	}
	readLine.close();
	return linesList;
    }
    
    public static DynamicLines getLines(String fileName) throws IOException {
	return getLines(fileName, null);
    }

    private static void callReadHandlers(String s, LinkedList<Consumer<String>> handler) {
	if (!handler.isEmpty()) {
	    for (Consumer<String> c: handler) {
		c.accept(s);
	    }
	}
    }
}
