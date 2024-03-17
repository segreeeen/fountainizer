module fountainizer.parser {
    requires java.desktop;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.apache.pdfbox;
    exports at.hsol.fountainizer.core.parser.api; // Replace with your chosen package name
    exports at.hsol.fountainizer.gui.simpleGuiv1;
    opens at.hsol.fountainizer.gui.simpleGuiv1.controller;
}