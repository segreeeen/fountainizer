package at.hsol.fountainizer.gui.i18n;

import java.util.*;

public class I18n {
	
	static public void setLocale(Locale locale){
		bundle = ResourceBundle.getBundle("net.swenf.scriptxt.i18n.MessagesBundle", locale);
	}
	
	static public List<Locale> getAvaiableLocales(){
		List<Locale> list = new LinkedList<Locale>(Arrays.stream(Locale.getAvailableLocales()).toList());
		return list;
	}
	
	static public ResourceBundle bundle = ResourceBundle.getBundle(
			"net.swenf.scriptxt.i18n.MessagesBundle", Locale.getDefault());

	static public String stateReady() {
		return bundle.getString("stateReady");
	}

	static public String stateChooseFile() {
		return bundle.getString("stateChooseFile");
	}

	static public String stateDone() {
		return bundle.getString("stateDone");
	}
	static public String messageHeader() {
		return bundle.getString("messageHeader");
	}
	static public String buttonLabelOpenPDF() {
		return bundle.getString("buttonLabelOpenPDF");
	}
	static public String buttonLabelMakePDF() {
		return bundle.getString("buttonLabelMakePDF");
	}
	static public String buttonLabelChooseTxt() {
		return bundle.getString("buttonLabelChooseTxt");
	}
	static public String progressLabel() {
		return bundle.getString("progressLabel");
	}
	static public String labelPathToScript() {
		return bundle.getString("labelPathToScript");
	}
	static public String errorReadWrite() {
		return bundle.getString("errorReadWrite");
	}
	static public String errorProgressingFile() {
		return bundle.getString("errorProgressingFile");
	}
	static public String errorCouldNotOpenFile() {
		return bundle.getString("errorCouldNotOpenFile");
	}

}
