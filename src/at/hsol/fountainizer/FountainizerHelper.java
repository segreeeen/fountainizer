package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;

import at.hsol.fountainizer.io.FilePrinter;
import at.hsol.fountainizer.io.FileReader;
import at.hsol.fountainizer.parser.Parser;
import at.hsol.fountainizer.parser.data.Statistic;
import at.hsol.fountainizer.parser.line.DynamicLines;

/**
 * Use this class to read, parse and print.
 * 
 * @author Felix Batusic
 * @author Thomas Sulzbacher
 * @version 0.2
 */
public class FountainizerHelper {
	private String fileIn;
	private String fileOut;
	private DynamicLines textlines = null;
	private Statistic stats;
	

	public FountainizerHelper(String fileIn, String fileOut) {
		if (fileIn != null && fileOut != null) {
			this.fileIn = fileIn;
			this.fileOut = fileOut;
			this.stats = null;
		} else {
			throw new IllegalArgumentException("input/output file can't be null");
		}
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
	public double read() throws IOException {
		long time = System.currentTimeMillis();
		textlines = FileReader.getLines(fileIn);
		return (System.currentTimeMillis() - time)/1000d;
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
	public double parse() throws IllegalStateException {
	    	Parser parser = new Parser();
		if (textlines != null) {
			long time = System.currentTimeMillis();
			parser.parse(textlines);
			time = (long) ((System.currentTimeMillis() - time)/1000d);
			this.stats = parser.getStats();
			return time;
		} else {
			throw new IllegalStateException("nothing has been read. use read() first.");
		}
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
	public double printPdf() throws IOException, URISyntaxException {
		long time = System.currentTimeMillis();
		FilePrinter.writePDFBox(textlines, fileOut);
		return (System.currentTimeMillis() - time)/1000d;
	}
	
	/**
	 * 
	 * @return Number of Parsed Lines or -1 if not parsed yet
	 */
	public int numOfLines() {
		return textlines != null ? textlines.getLineCount() : -1;
	}
	
	public Statistic getStats() {
	    return stats;
	}
}
