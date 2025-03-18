package at.hsol.fountainizer.ui.simplegui.simpleGuiv1.controller;

import at.hsol.fountainizer.core.api.Options;
import at.hsol.fountainizer.ui.simplegui.simpleGuiv1.Fountainizer;
import at.hsol.fountainizer.ui.simplegui.simpleGuiv1.log.Dump;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;

public class MainWindowController {
    private final Stage stage;

    private boolean lockPressed;

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

    public MainWindowController(Stage stage) {
        this.stage = stage;
        this.lockPressed = false;
        initialize();
        String BUILD = "v0.5 beta build 5ad8763 20.01.2016 15:19";
        this.versionInfo.setText(BUILD);
        stage.setTitle("Fountainizer - SimpleGui v0.5 beta");
        stage.setResizable(false);
        stage.show();
    }

    private void initialize() {
        AnchorPane root = null;
        FXMLLoader loader = new FXMLLoader(Fountainizer.class.getResource("view/MainWindow.fxml"));
        loader.setController(this);
        try {
            root = (AnchorPane)loader.load();
        } catch (IOException e) {
            System.err.println("Something terrible happened! also ficken SIE sich!");
            Dump.thatShit(e);
            e.printStackTrace();
        }
        try {
            loadIconInto(this.stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        activateDragDropListener();
        Scene scene = new Scene((Parent)root);
        this.stage.setScene(scene);
    }

    private void loadIconInto(Stage stage) throws IOException {
        Image image = new Image(Objects.<InputStream>requireNonNull(Fountainizer.class.getResourceAsStream("img/icon.png")));
        stage.getIcons().add(image);
        if (System.getProperty("os.name").contains("Mac")) {
            try {
                Class<?> c = Class.forName("com.apple.eawt.Application");
                Method m = c.getMethod("getApplication", new Class[0]);
                Object applicationInstance = m.invoke(null, new Object[0]);
                m = applicationInstance.getClass().getMethod("setDockIconImage", new Class[] { Image.class });
                m.invoke(applicationInstance, new Object[] { image });
            } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|ClassNotFoundException|NoSuchMethodException|SecurityException e) {
                e.printStackTrace();
                Dump.thatShit("mac dock image error :D", e);
            }
        }
    }

    private void activateDragDropListener() {
        this.txtResourcePath.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(new TransferMode[] { TransferMode.LINK });
            } else {
                event.consume();
            }
        });
        this.txtResourcePath.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                Optional<File> o = db.getFiles().stream()
                        .filter(a -> a.getAbsolutePath().endsWith(".txt") || a.getAbsolutePath().endsWith(".fountain"))
                        .findFirst();

                if (o.isPresent()) {
                    File f = o.get();
                    this.txtResourcePath.setText(f.getAbsolutePath());
                    String path = createNewPath(f);
                    this.txtTargetPath.setText(path);
                    this.show.setDisable(true);
                    this.infobox.setText("");
                } else {
                    this.infobox.setText("Please put in valid files! Supported formats are: .txt and .fountain!");
                }
            }
        });
    }

    @FXML
    void createPDF(ActionEvent event) {
        double readTime, printTime;
        this.infobox.setText("Creating pdf...");
        String txt = this.txtTargetPath.getText();
        if (this.txtTargetPath.getText().equals("")) {
            String msg = "ERROR!   You have to set source and destination!";
            this.infobox.setText(msg);
            Dump.thatShit(msg);
            return;
        }
        File exportFile = new File(this.txtTargetPath.getText());
        if (exportFile.exists()) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Overwrite?");
            a.setContentText("File at \"" + exportFile.getPath() + "\" already exists. Are you sure you want to overwrite the existing file?");
            Optional<ButtonType> o = a.showAndWait();
            o.ifPresent(btn -> {
                if (btn.getText().contains(ButtonType.CANCEL.getText()))
                    this.infobox.setText(this.infobox.getText() + "   User aborted...");
            });
        }
        String source = this.txtResourcePath.getText();
        String dest = this.txtTargetPath.getText();
        Options options = new Options();
        options.setPrintTitlePage(true);
        options.setPrintTakeNumbers(true);
        options.setPrintCharacterPage(true);
        FountainizerController api = new FountainizerController();
        try {
            api.processFile(source, dest, options);
            readTime = api.getReadTime();
            printTime = api.getPrintTime();
        } catch (IOException e) {
            this.infobox.setText("Error opening File!");
            Dump.thatShit("Error opening File!", e);
            e.printStackTrace();
            return;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        this.infobox.setText("!!!   Document successfully created   !!!");
        this.infobox.appendText("\nRead and parsed your document in " + df.format(readTime) + "seconds\n and printed " + api
                .numOfLines() + " lines in only " + df.format(printTime) + " seconds :D!");
        this.show.setDisable(false);
    }

    @FXML
    void chooseSource(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose txt-File to parse...");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt", new String[] { "*.txt" }));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".fountain", new String[] { "*.fountain" }));
        File selectedFile = fc.showOpenDialog((Window)this.stage);
        if (selectedFile == null) {
            this.infobox.setText("Please select a valid *.txt or *.fountain-file!");
            return;
        }
        this.txtResourcePath.setText(selectedFile.getAbsolutePath());
        String path = createNewPath(selectedFile);
        this.txtTargetPath.setText(path);
        this.show.setDisable(true);
        this.infobox.setText("");
    }

    private String createNewPath(File f) {
        String path = f.getParentFile().toString() + f.getParentFile().toString();
        String name = f.getName();
        String newName = name.endsWith(".txt") ? f.getName().substring(0, name.length() - 4) : name.substring(0, name.length() - 9);
        return path + path + ".pdf";
    }

    @FXML
    void chooseDest(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose txt-File to parse...");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".pdf", new String[] { "*.pdf" }));
        File selectedFile = fc.showSaveDialog((Window)this.stage);
        if (selectedFile == null) {
            this.infobox.setText("Please choose valid path and name for file!");
            return;
        }
        this.txtTargetPath.setText(selectedFile.getAbsolutePath());
        this.show.setDisable(true);
        this.infobox.setText("");
    }

    @FXML
    void showPdf(ActionEvent event) throws IOException {
        if (!System.getProperty("os.name").contains("Mac") && !System.getProperty("os.name").contains("Windows")) {
            Alert b = new Alert(Alert.AlertType.INFORMATION, "Not supported on your os!", new ButtonType[] { ButtonType.OK });
            b.showAndWait();
            return;
        }
        File exportFile = new File(this.txtTargetPath.getText());
        if (exportFile.exists()) {
            Desktop.getDesktop().open(exportFile);
            this.infobox.setText("File opened!");
        } else {
            this.infobox.setText("File does not exist!");
            this.show.setDisable(true);
        }
    }

    @FXML
    void changeDestPath(ActionEvent event) {
        this.txtTargetPath.setDisable(this.lockPressed);
        this.btnChooseDest.setDisable(this.lockPressed);
        this.lockPressed = !this.lockPressed;
    }

    @FXML
    void createPath(ActionEvent event) {
        String path = this.txtResourcePath.getText();
        if (path.equals("")) {
            this.infobox.setText("Please set source-path before using this function!");
        } else {
            File f = new File(path);
            if (f.exists()) {
                this.txtTargetPath.setText(createNewPath(f));
                this.infobox.setText("Path created!");
            } else {
                this.infobox.setText("File at specified path does not exist! Please check!");
            }
        }
    }
}
