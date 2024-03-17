package at.hsol.fountainizer.ui.simplegui.simpleGuiv1.controller;

import at.hsol.fountainizer.core.api.parser.Options;
import at.hsol.fountainizer.core.api.parser.Parser;
import at.hsol.fountainizer.core.api.parser.Printer;
import at.hsol.fountainizer.parser.ParserFactory;
import at.hsol.fountainizer.printer.pdf.PDFPrinter;
import at.hsol.fountainizer.ui.simplegui.simpleGuiv1.Fountainizer;
import at.hsol.fountainizer.ui.simplegui.simpleGuiv1.log.Dump;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;

/**
 * This class is a controller class that loads the Mainwindow.fxml and offers
 * methods that are used by the gui.
 *
 * @author Thomas Sulzbacher
 * @version 0.2
 */
public class MainWindowController {

    /**
     * Primary stage which is currently displayed
     */
    private final Stage stage;

    private boolean lockPressed;

    public MainWindowController(Stage stage) {
        this.stage = stage;
        lockPressed = false;

        initialize();

        /*
         * Contains the latest date of the latest Build
         */
        String BUILD = "v0.5 beta build 5ad8763 20.01.2016 15:19";
        versionInfo.setText(BUILD);
        stage.setTitle("Fountainizer - SimpleGui v0.5 beta");
        stage.setResizable(false);
        stage.show();
    }

    private void initialize() {
        AnchorPane root = null;

        // load layout
        FXMLLoader loader = new FXMLLoader(Fountainizer.class.getResource("view/MainWindow.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            System.err.println("Something terrible happened! also ficken SIE sich!");
            Dump.thatShit(e);
            e.printStackTrace();
        }

        // load icons
        try {
            loadIconInto(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // activate listeners
        activateDragDropListener();

        // set Scene
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    private void loadIconInto(Stage stage) throws IOException {
        // loads on windows machines
        stage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(Fountainizer.class.getResourceAsStream("img/icon.png"))));

        // loads icon on mac osx
        if (System.getProperty("os.name").contains("Mac")) {
            java.awt.Image image = ImageIO.read(Objects.requireNonNull(Fountainizer.class.getResourceAsStream("img/icon.png")));

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
                e.printStackTrace();
                Dump.thatShit("mac dock image error :D", e);
            }
        }
    }

    private void activateDragDropListener() {
        txtResourcePath.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.LINK);
            } else {
                event.consume();
            }
        });

        txtResourcePath.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();

            if (db.hasFiles()) {
                Optional<File> o = db.getFiles().stream()
                        .filter(a -> a.getAbsolutePath().endsWith(".txt") || a.getAbsolutePath().endsWith(".fountain"))
                        .findFirst();

                if (o.isPresent()) {
                    File f = o.get();
                    txtResourcePath.setText(f.getAbsolutePath());
                    String path = createNewPath(f);
                    txtTargetPath.setText(path);
                    show.setDisable(true);
                    infobox.setText("");
                } else {
                    infobox.setText("Please put in valid files! Supported formats are: .txt and .fountain!");
                }

            }
        });
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
    void createPDF(ActionEvent event) {
        infobox.setText("Creating pdf...");
        String txt = txtTargetPath.getText();
        if (txtTargetPath.getText().equals("")) {
            String msg = "ERROR!   You have to set source and destination!";
            infobox.setText(msg);
            Dump.thatShit(msg);
            return;
        }

        File exportFile = new File(txtTargetPath.getText());

        if (exportFile.exists()) {
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setTitle("Overwrite?");
            a.setContentText("File at \"" + exportFile.getPath()
                    + "\" already exists. Are you sure you want to overwrite the existing file?");
            Optional<ButtonType> o = a.showAndWait();

            o.ifPresent(btn -> {
                if (btn.getText().contains(ButtonType.CANCEL.getText())) {
                    infobox.setText(infobox.getText() + "   User aborted...");
                }
            });
        }


        String source = txtResourcePath.getText();
        String dest = txtTargetPath.getText();
        Printer pdfPrinter = PDFPrinter.create();

        Options options = new Options();
        options.setPrintTitlePage(true);
        options.setPrintTakeNumbers(true);
        options.setPrintCharacterPage(true);

        Parser api = ParserFactory.create(source, dest, pdfPrinter, options);
        double readTime, parseTime, printTime;

        // ****************** READ *********************
        try {
            readTime = api.read();
        } catch (
                IOException e) {
            infobox.setText("Error opening File!");
            Dump.thatShit("Error opening File!", e);
            e.printStackTrace();
            return;
        }
        // ****************** PARSE ********************
        try {
            parseTime = api.parse();
        } catch (
                IllegalStateException e) {
            infobox.setText("Error parsing file!");
            Dump.thatShit("Error parsing File!", e);
            e.printStackTrace();
            return;
        }

        // ****************** PRINT ********************
        try {
            printTime = api.print();
        } catch (IOException |
                 URISyntaxException e) {
            infobox.setText("Error writing File!");
            Dump.thatShit("Error writing File!", e);
            e.printStackTrace();
            return;
        }

        double rP = readTime + parseTime;
        DecimalFormat df = new DecimalFormat("#.##");
        infobox.setText("!!!   Document successfully created   !!!");
        infobox.appendText("\nRead and parsed your document in " + df.format(rP) + "seconds\n" + " and printed "
                + api.numOfLines() + " lines in only " + df.format(printTime) + " seconds :D!");
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
        show.setDisable(true);
        infobox.setText("");
    }

    @FXML
    void showPdf(ActionEvent event) throws IOException {
        if (!System.getProperty("os.name").contains("Mac") && !System.getProperty("os.name").contains("Windows")) {
            Alert b = new Alert(AlertType.INFORMATION, "Not supported on your os!", ButtonType.OK);
            b.showAndWait();
            return;
        }
        File exportFile = new File(txtTargetPath.getText());
        if (exportFile.exists()) {
            Desktop.getDesktop().open(exportFile);
            infobox.setText("File opened!");
        } else {
            infobox.setText("File does not exist!");
            show.setDisable(true);
        }
    }

    @FXML
    void changeDestPath(ActionEvent event) {
        txtTargetPath.setDisable(lockPressed);
        btnChooseDest.setDisable(lockPressed);
        lockPressed = !lockPressed;
    }

    @FXML
    void createPath(ActionEvent event) {
        String path = txtResourcePath.getText();
        if (path.equals("")) {
            infobox.setText("Please set source-path before using this function!");
        } else {
            File f = new File(path);
            if (f.exists()) {
                txtTargetPath.setText(createNewPath(f));
                infobox.setText("Path created!");
            } else {
                infobox.setText("File at specified path does not exist! Please check!");
            }
        }

    }

}
