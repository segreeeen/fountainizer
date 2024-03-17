package at.hsol.fountainizer.core.parser;

import at.hsol.fountainizer.Options;
import at.hsol.fountainizer.core.parser.api.CharacterInfo;
import at.hsol.fountainizer.core.parser.api.ParserAPI;
import at.hsol.fountainizer.core.parser.api.Printer;
import at.hsol.fountainizer.core.parser.api.Statistics;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Use this class to read, parse and print.
 * 
 * @author Felix Batusic
 * @author Thomas Sulzbacher
 * @version 0.2
 */
public class ParserController implements ParserAPI {
	private final FountainParser parser;
	private final String fileIn;
	private final String fileOut;
	private final Printer printer;

	public ParserController(String fileIn, String fileOut, Printer printer, Options options) {
		if (fileIn != null && fileOut != null) {
			this.fileIn = fileIn;
			this.fileOut = fileOut;
			this.printer = printer;
			this.printer.setOptions(options);
			this.parser = new FountainParser(options);
		} else {
			throw new IllegalArgumentException("input/output file can't be null");
		}
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
	@Override
	public double read() throws IOException {
		long time = System.currentTimeMillis();
		parser.readFile(fileIn);
		return (System.currentTimeMillis() - time) / 1000d;
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
	@Override
	public double parse() throws IllegalStateException {
			long time = System.currentTimeMillis();
			parser.parseDocument();
			time = (long) ((System.currentTimeMillis() - time) / 1000d);
			return time;
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
	@Override
	public double print() throws IOException, URISyntaxException {
		long time = System.currentTimeMillis();
		printer.print(parser.getContent(), fileOut);
		return (System.currentTimeMillis() - time) / 1000d;
	}

	/**
	 * 
	 * @return Number of Parsed Lines or -1 if not parsed yet
	 */
	@Override
	public int numOfLines() {
		return parser.getContent().getLineCount();
	}

	@Override
	public Statistics getStats() {
		return parser.getContent().getStats();
	}

	@Override
	public List<? extends CharacterInfo>  getCharacterStats() {
		return parser.getContent().getStats().getCharacterStats();
	}
}
