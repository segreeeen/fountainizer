package at.hsol.fountainizer.ui.simplegui.simpleGuiv1.controller;

import at.hsol.fountainizer.core.api.parser.*;
import at.hsol.fountainizer.parser.FountainParserFactory;
import at.hsol.fountainizer.printer.pdf.PdfPrinterFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Use this class to read, parse and print.
 * 
 * @author Felix Batusic
 * @author Thomas Sulzbacher
 * @version 0.2
 */
public class ParserController {

	

	public void processFile(String fileIn, String fileOut, Options options) throws IOException {
		if (fileIn != null && fileOut != null) {
			String fileAsString = Files.readString(Path.of(fileIn));

			// as Parser we choose FountainParser
			Parser parser = FountainParserFactory.create(options);

			// as Printer we choose PDF Printer
			Printer printer = PdfPrinterFactory.create();

			// now we parse
			Content content = parser.parse(fileAsString);

			//now we print
			printer.print(content, fileOut);


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
		parser.readFile(fileIn);
		return (System.currentTimeMillis() - time) / 1000d;
	}

	/**
	 * 
	 * @return measured time in seconds
	 * @throws IOException
	 */
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
	public double print() throws IOException, URISyntaxException {
		long time = System.currentTimeMillis();
		printer.print(parser.getContent(), fileOut);
		return (System.currentTimeMillis() - time) / 1000d;
	}

	/**
	 * 
	 * @return Number of Parsed Lines or -1 if not parsed yet
	 */
	public int numOfLines() {
		return parser.getContent().getLineCount();
	}

	public Statistics getStats() {
		return parser.getContent().getStats();
	}

	public List<? extends CharacterInfo>  getCharacterStats() {
		return parser.getContent().getStats().getCharacterStats();
	}
}
