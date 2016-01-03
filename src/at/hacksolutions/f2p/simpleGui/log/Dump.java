package at.hacksolutions.f2p.simpleGui.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is intended to quickly and dirty dump information to a file. Used
 * only in beta phase... Implementation of Java-Logger class pending...
 * 
 * @author Thomas Sulzbacher
 *
 */
public class Dump {

	private final static String fileName = "fountain_dump.txt";
	private final static String filePath = System.getProperty("user.dir");
	private final static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy '-' HH:mm:ss");

	/**
	 * Logs the input parameter to a txt file.
	 * 
	 * @param s
	 */
	public static void thatShit(String s) {
		File f = new File(filePath + File.separator + fileName);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logSysInfo(f);
		}

		try {
			log(s, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see thatShit(String s) Logs an exception to a file!
	 * @param e
	 */
	public static void thatShit(Exception e) {
		thatShit(e.toString());
	}

	public static void thatShit(String s, Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		thatShit(s + exceptionAsString);
	}

	private static void log(String s, File f) throws IOException {
		FileWriter fileWriter = new FileWriter(f, true);
		BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
		bufferWriter.newLine();
		bufferWriter.write(sdf.format(new Date()));
		bufferWriter.newLine();
		bufferWriter.write(s);
		bufferWriter.newLine();
		bufferWriter.close();
		fileWriter.close();
	}

	/**
	 * This method logs all available system data to actions.log at startup!
	 */
	private static void logSysInfo(File f) {

		try {
			FileWriter fileWriter = new FileWriter(f, true);
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.newLine();
			bufferWriter.write(sdf.format(new Date()));
			bufferWriter.newLine();
			bufferWriter.write(System.getProperty("java.vendor"));
			bufferWriter.newLine();
			bufferWriter.write(System.getProperty("java.version"));
			bufferWriter.newLine();
			bufferWriter.write(System.getProperty("os.arch"));
			bufferWriter.newLine();
			bufferWriter.write(System.getProperty("os.name"));
			bufferWriter.newLine();
			bufferWriter.write(System.getProperty("os.version"));
			bufferWriter.newLine();
			bufferWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
