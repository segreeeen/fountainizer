package at.hsol.fountainizer.web.controller;

import at.hsol.fountainizer.web.service.FountainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PreviewController {

    FountainService fountainService;

    @Autowired
    public PreviewController(FountainService fountainService) {
        this.fountainService = fountainService;
    }

    @MessageMapping("/preview")      // client sendet an /app/preview
    @SendTo("/topic/preview")       // alle Listener auf /topic/preview bekommen das
    public String preview(String source) {
        return fountainService.createPreview(source);
    }

}
