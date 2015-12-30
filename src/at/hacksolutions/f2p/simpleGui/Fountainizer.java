package at.hacksolutions.f2p.simpleGui;

import at.hacksolutions.f2p.simpleGui.controller.MainWindowController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class starts the SimpleGui Version for Fountainizer
 * @author Thomas Sulzbacher
 *
 */
public class Fountainizer extends Application{

	public static void main(String[] args) {
		Fountainizer.launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		new MainWindowController(arg0);
	}
	
}
