package at.hacksolutions.f2p.simpleGui.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.pdfbox.exceptions.COSVisitorException;

import at.hacksolutions.f2p.io.FilePrinter;
import at.hacksolutions.f2p.io.FileReader;
import at.hacksolutions.f2p.parser.Parser;
import at.hacksolutions.f2p.parser.line.DynamicLines;
import at.hacksolutions.f2p.simpleGui.Fountainizer;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class is a controller class that loads the Mainwindow.fxml and offers
 * methods that are used by the gui.
 * 
 * @author Thomas Sulzbacher
 * @version 0.1
 *
 */
public class MainWindowController {

	private final String BUILD = "Build 6fdc3c4 30.12.2015 19:47";
	private Stage stage;
	private AnchorPane root;
	private File exportFile;

	public MainWindowController(Stage stage) {
		this.stage = stage;
		initialize();
		versionInfo.setText(BUILD);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Fountainizer v0.1");
		stage.show();
	}

	private void initialize() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				Fountainizer.class.getResource(File.separator + "view" + File.separator + "MainWindow.fxml"));
		try {
			loader.setController(this);
			root = loader.load();
		} catch (IOException e) {
			System.err.println("Something terrible happened! also ficken SIE sich!");
			e.printStackTrace();
			System.exit(0);
		}
	}

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
	void createPDF(ActionEvent event) {
		infobox.setText("Creating pdf...");
		if(exportFile == null) {
			infobox.setText("ERROR!   You have to set source and destination!");
			return;
		}
		if (exportFile.exists()) {
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("Overwrite?");
			a.setContentText("File at \"" + exportFile.getPath() + "\" already exists. Are you sure you want to overwrite the existing file?");
			Optional<ButtonType> o = a.showAndWait();
			if (o.get().getText().contains(ButtonType.CANCEL.getText())) {
				infobox.setText(infobox.getText() + "   User aborted...");
				return;
			}
		}
		String source = txtResourcePath.getText();
		String dest = txtTargetPath.getText();
		DynamicLines lines = null;

		try {
			lines = FileReader.getLines(source);
		} catch (IOException e) {
			infobox.setText("Error opening File!");
			e.printStackTrace();
			return;
		}

		Parser.parse(lines);

		try {
			FilePrinter.writePDFBox(lines, dest);
		} catch (COSVisitorException | IOException e) {
			infobox.setText("Error writing File!");
			e.printStackTrace();
			return;
		}

		infobox.setText("!!!   Document successfully created   !!!");
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
			infobox.setText("Please select a valid *.txt-file!");
			return;
		}
		txtResourcePath.setText(selectedFile.getAbsolutePath());

		String path = createNewPath(selectedFile);
		exportFile = new File(path);
		txtTargetPath.setText(path);
		show.setDisable(true);
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

}
