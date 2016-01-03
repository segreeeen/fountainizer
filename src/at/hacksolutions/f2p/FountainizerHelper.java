package at.hacksolutions.f2p;

import java.io.IOException;
import java.net.URISyntaxException;

import at.hacksolutions.f2p.io.FilePrinter;
import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.line.DynamicLines;
import at.hacksolutions.f2p.parser.Parser;

/**
 * Use this class to read, parse and print.
 * 
 * @author Felix Batusic
 */
public class FountainizerHelper {
    private String fileIn;
    private String fileOut;
    private DynamicLines textlines = null;

    public FountainizerHelper(String fileIn, String fileOut) {
	if (fileIn != null && fileOut != null) {
	    this.fileIn = fileIn;
	    this.fileOut = fileOut;
	} else {
	    throw new IllegalArgumentException("input/output file can't be null");
	}
    }

    /**
     * 
     * @return measured time in ms
     * @throws IOException
     */
    public long read() throws IOException {
	long time = System.currentTimeMillis();
	FileReader.getLines(fileIn);
	return System.currentTimeMillis() - time;
    }

    /**
     * 
     * @return measured time in ms
     * @throws IOException
     */
    public long parse() {
	if (textlines != null) {
	    long time = System.currentTimeMillis();
	    Parser.parse(textlines);
	    return System.currentTimeMillis() - time;
	} else {
	    throw new IllegalStateException("nothing has been read. use read() first.");
	}
    }

    /**
     * 
     * @return measured time in ms
     * @throws IOException
     */
    public long printPdf() throws IOException, URISyntaxException {
	long time = System.currentTimeMillis();
	FilePrinter.writePDFBox(textlines, fileOut);
	return System.currentTimeMillis() - time;
    }
}
