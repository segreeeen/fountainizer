package at.hsol.fountainizer.ui.simplegui.simpleGuiv1.controller;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.core.api.parser.Content;
import at.hsol.fountainizer.core.api.parser.Parser;
import at.hsol.fountainizer.core.api.printer.FilePrinter;
import at.hsol.fountainizer.parser.FountainParserFactory;
import at.hsol.fountainizer.printer.html.HtmlPrinterFactory;
import at.hsol.fountainizer.printer.pdf.PdfPrinterFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FountainizerController {
	private double readTime = 0.0D;

	private double printTime = 0.0D;

	private int numOfLines = 0;

	public void processFile(String fileIn, String fileOut, Options options) throws IOException {
		if (fileIn != null && fileOut != null) {
			String fileAsString = Files.readString(Path.of(fileIn), StandardCharsets.UTF_8);
			Parser parser = FountainParserFactory.create(options);
			FilePrinter printerAPI = PdfPrinterFactory.createFilePrinter(options);
            FilePrinter printerHtml = HtmlPrinterFactory.createFilePrinter(options);

			long time = System.currentTimeMillis();
			Content content = parser.parse(fileAsString);
			this.readTime = (System.currentTimeMillis() - time) / 1000.0D;
			time = System.currentTimeMillis();
			printerAPI.printToFile(content, fileOut);
            String fileOutHtml = fileOut.replace(".pdf", ".html");
            printerHtml.printToFile(content, fileOutHtml);
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
