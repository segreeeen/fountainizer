module fountainizer.web {
    requires fountainizer.api;
    requires fountainizer.parser;
    requires fountainizer.printer.pdf;
    requires fountainizer.printer.html;
    requires spring.context;
    requires spring.beans;
    requires spring.messaging;
    requires spring.web;
    requires org.apache.tomcat.embed.core;
}