package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;

import at.hsol.fountainizer.io.FilePrinter;
import at.hsol.fountainizer.io.FileReader;
import at.hsol.fountainizer.parser.Parser;
import at.hsol.fountainizer.parser.line.DynamicLines;
import at.hsol.fountainizer.parser.types.Statistic;
import at.hsol.fountainizer.pdfbox.PagerOptions;

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
	private PagerOptions options;
	

	public FountainizerHelper(String fileIn, String fileOut, PagerOptions options) {
		if (fileIn != null && fileOut != null) {
			this.fileIn = fileIn;
			this.fileOut = fileOut;
			this.stats = null;
			this.options = options;
		} else {
			throw new IllegalArgumentException("input/output file can't be null");
		}
	}
	
	public FountainizerHelper(String fileIn, String fileOut) {
	    this(fileIn, fileOut, new PagerOptions());
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
	    	Parser parser = new Parser(textlines);
		if (textlines != null) {
			long time = System.currentTimeMillis();
			parser.parse();
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
		if (options != null) {
		    FilePrinter.writePDFBox(textlines, fileOut, options);
		} else {
		    FilePrinter.writePDFBox(textlines, fileOut, null);
		}
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
