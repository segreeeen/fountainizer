package at.hsol.fountainizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import at.hsol.fountainizer.io.FilePrinter;
import at.hsol.fountainizer.io.FileReader;
import at.hsol.fountainizer.parser.Parser;
import at.hsol.fountainizer.parser.content.ParserContent;
import at.hsol.fountainizer.parser.meta.FCharacter;
import at.hsol.fountainizer.parser.meta.Statistic;

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
	private ParserContent textlines = null;
	private Statistic stats;
	private Options options;
	private FilePrinter fp;

	public FountainizerHelper(String fileIn, String fileOut, Options options) {
		if (fileIn != null && fileOut != null) {
			this.fileIn = fileIn;
			this.fileOut = fileOut;
			this.stats = null;
			this.options = options;
			this.fp = new FilePrinter(options);
		} else {
			throw new IllegalArgumentException("input/output file can't be null");
		}
	}

	public FountainizerHelper(String fileIn, String fileOut) {
		this(fileIn, fileOut, new Options());
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
	public double read() throws IOException {
		long time = System.currentTimeMillis();
		textlines = FileReader.getLines(fileIn);
		return (System.currentTimeMillis() - time) / 1000d;
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
	public double parse() throws IllegalStateException {
		Parser parser = new Parser(textlines, options);
		if (textlines != null) {
			long time = System.currentTimeMillis();
			parser.parse();
			time = (long) ((System.currentTimeMillis() - time) / 1000d);
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
		fp.writePDFBox(textlines, fileOut, stats);
		return (System.currentTimeMillis() - time) / 1000d;
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

	public List<FCharacter> getCharacterStats() {
		return stats.getCharacterStats(options);
	}
}
