package at.hacksolutions.f2p.simpleGui.controller;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.imageio.ImageIO;

import at.hacksolutions.f2p.FountainizerHelper;
import at.hacksolutions.f2p.io.FilePrinter;
import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.line.DynamicLines;
import at.hacksolutions.f2p.simpleGui.Fountainizer;
import at.hacksolutions.f2p.simpleGui.log.Dump;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class is a controller class that loads the Mainwindow.fxml and offers
 * methods that are used by the gui.
 * 
 * @author Thomas Sulzbacher
 * @version 0.2
 *
 */
public class MainWindowController {

	private final String BUILD = "v0.5 beta build 7544d61 03.01.2016 12:00";
	private final Stage stage;
	private AnchorPane root;
	private File exportFile;
	private boolean pressed;

	public MainWindowController(Stage stage) {
		this.stage = stage;
		pressed = false;
		initialize();
		versionInfo.setText(BUILD);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Fountainizer - SimpleGui v0.5 beta");
		stage.setResizable(false);
		try {
			loadIconInto(stage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.show();
	}

	private void initialize() {
		FXMLLoader loader = new FXMLLoader(
				Fountainizer.class.getResource("/at/hacksolutions/f2p/simpleGui/view/MainWindow.fxml"));

		try {
			loader.setController(this);
			root = loader.load();
		} catch (IOException e) {
			System.err.println("Something terrible happened! also ficken SIE sich!");
			Dump.thatShit(e);
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void loadIconInto(Stage stage) throws IOException {
		Image img = new Image(Fountainizer.class.getResourceAsStream("/at/hacksolutions/f2p/simpleGui/img/icon.png"));
		stage.getIcons().add(img);

		if (System.getProperty("os.name").contains("Mac")) {
			java.awt.Image image = ImageIO
					.read(Fountainizer.class.getResourceAsStream("/at/hacksolutions/f2p/simpleGui/img/icon.png"));

			try {
				@SuppressWarnings("rawtypes")
				Class c = Class.forName("com.apple.eawt.Application");
				@SuppressWarnings("unchecked")
				Method m = c.getMethod("getApplication");
				Object applicationInstance = m.invoke(null);
				m = applicationInstance.getClass().getMethod("setDockIconImage", java.awt.Image.class);

				m.invoke(applicationInstance, image);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| ClassNotFoundException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Dump.thatShit("mac dock image error :D", e);
			}
		}
	}

	// ***************************************************************************//
	// FXML Specific Section //
	// ***************************************************************************//

	@FXML
	private Label versionInfo;

	@FXML
	private TextField txtTargetPath;

	@FXML
	private TextField txtResourcePath;

	@FXML
	private TextArea infobox;

	@FXML
	private Button show;

	@FXML
	private Button btnChooseDest;

	@FXML
	void createPDF(ActionEvent event) throws URISyntaxException {

		infobox.setText("Creating pdf...");
		
		if (exportFile == null) {
			infobox.setText("ERROR!   You have to set source and destination!");
			Dump.thatShit("Function Create PDF ERROR!   You have to set source and destination!");
			return;
		}
		if (exportFile.exists()) {
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("Overwrite?");
			a.setContentText("File at \"" + exportFile.getPath()
					+ "\" already exists. Are you sure you want to overwrite the existing file?");
			Optional<ButtonType> o = a.showAndWait();
			if (o.get().getText().contains(ButtonType.CANCEL.getText())) {
				infobox.setText(infobox.getText() + "   User aborted...");
				return;
			}
		}
		
		String source = txtResourcePath.getText();
		String dest = txtTargetPath.getText();
		
		FountainizerHelper api = new FountainizerHelper(source, dest);
		double readTime = 0, parseTime = 0, printTime = 0;
		
		//****************** READ *********************
		try {
			readTime = api.read();
		} catch (IOException e) {
			infobox.setText("Error opening File!");
			Dump.thatShit("Error opening File!", e);
			e.printStackTrace();
			return;
		}
		//****************** PARSE ********************
		try {
			parseTime = api.parse();
		} catch(IllegalStateException e) {
			infobox.setText("Error parsing file!");
			Dump.thatShit("Error parsing File!", e);
			e.printStackTrace();
			return;
		}

		//****************** PRINT ********************
		try {
			printTime = api.printPdf();
		} catch (IOException | URISyntaxException e) {
			infobox.setText("Error writing File!");
			Dump.thatShit("Error writing File!", e);
			e.printStackTrace();
			return;
		}
		double rP = readTime + parseTime;
		infobox.setText("!!!   Document successfully created   !!!");
		infobox.appendText("\nRead and parsed your document in " + rP + "seconds\n" 
							+ " and printed " + api.numOfLines()
				+ " lines in only " + printTime + " seconds :D!");
		show.setDisable(false);

	}

	@FXML
	void chooseSource(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose txt-File to parse...");
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt", "*.txt"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".fountain", "*.fountain"));
		File selectedFile = fc.showOpenDialog(stage);
		if (selectedFile == null) {
			infobox.setText("Please select a valid *.txt or *.fountain-file!");
			return;
		}
		txtResourcePath.setText(selectedFile.getAbsolutePath());

		String path = createNewPath(selectedFile);
		exportFile = new File(path);
		txtTargetPath.setText(path);
		show.setDisable(true);
		infobox.setText("");
	}

	private String createNewPath(File f) {
		String path = f.getParentFile().toString() + File.separator;
		String name = f.getName();
		String newName = name.endsWith(".txt") ? f.getName().substring(0, name.length() - 4)
				: name.substring(0, name.length() - 9);
		return path + newName + ".pdf";
	}

	@FXML
	void chooseDest(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose txt-File to parse...");
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".pdf", "*.pdf"));
		File selectedFile = fc.showSaveDialog(stage);
		if (selectedFile == null) {
			infobox.setText("Please choose valid path and name for file!");
			return;
		}
		txtTargetPath.setText(selectedFile.getAbsolutePath());
		exportFile = new File(txtTargetPath.getText());
		show.setDisable(true);
		infobox.setText("");
	}

	@FXML
	void showPdf(ActionEvent event) throws IOException {
		if (exportFile.exists()) {
			Desktop.getDesktop().open(exportFile);
			infobox.setText("File opened!");
		} else {
			infobox.setText("File does not exist!");
		}
	}

	@FXML
	void changeDestPath(ActionEvent event) {
		pressed = !pressed;
		if (pressed) {
			txtTargetPath.setDisable(false);
			btnChooseDest.setDisable(false);
		} else {
			txtTargetPath.setDisable(true);
			btnChooseDest.setDisable(true);
		}
	}

}
