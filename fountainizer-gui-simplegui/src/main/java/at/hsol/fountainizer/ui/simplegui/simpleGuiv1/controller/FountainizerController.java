package at.hsol.fountainizer.ui.simplegui.simpleGuiv1.controller;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.parser.ParserAPI;
import at.hsol.fountainizer.core.api.printer.PrinterAPI;
import at.hsol.fountainizer.parser.FountainParserFactory;
import at.hsol.fountainizer.printer.pdf.PdfPrinterFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FountainizerController {
	private double readTime = 0.0D;

	private double printTime = 0.0D;

	private int numOfLines = 0;

	public void processFile(String fileIn, String fileOut, Options options) throws IOException {
		if (fileIn != null && fileOut != null) {
			String fileAsString = Files.readString(Path.of(fileIn, new String[0]));
			ParserAPI parser = FountainParserFactory.create(options);
			PrinterAPI printerAPI = PdfPrinterFactory.create(options);
			long time = System.currentTimeMillis();
			Content content = parser.parse(fileAsString);
			this.readTime = (System.currentTimeMillis() - time) / 1000.0D;
			time = System.currentTimeMillis();
			printerAPI.print(content, fileOut);
			this.printTime = (System.currentTimeMillis() - time) / 1000.0D;
			this.numOfLines = content.getLineCount();
		} else {
			throw new IllegalArgumentException("input/output file can't be null");
		}
	}

	public int numOfLines() {
		return this.numOfLines;
	}

	public double getReadTime() {
		return this.readTime;
	}

	public void setReadTime(double readTime) {
		this.readTime = readTime;
	}

	public double getPrintTime() {
		return this.printTime;
	}

	public void setPrintTime(double printTime) {
		this.printTime = printTime;
	}
}
