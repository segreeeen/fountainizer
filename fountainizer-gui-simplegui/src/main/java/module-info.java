module fountainizer.gui.simplegui {
    requires java.desktop;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    requires fountainizer.api;
    requires fountainizer.parser;
    requires fountainizer.printer.pdf;

    exports at.hsol.fountainizer.ui.simplegui.simpleGuiv1;
    opens at.hsol.fountainizer.ui.simplegui.simpleGuiv1.controller;
}