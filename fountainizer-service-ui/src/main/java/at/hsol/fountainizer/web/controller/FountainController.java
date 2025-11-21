package at.hsol.fountainizer.web.controller;

import at.hsol.fountainizer.web.service.FountainService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class FountainController {

    final FountainService fountainService;

    public FountainController(FountainService fountainService) {
        this.fountainService = fountainService;
    }

    @PostMapping(
            value = "/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE,
            consumes = MediaType.TEXT_PLAIN_VALUE // Specify expected input type
    )
    public @ResponseBody byte[] getFile(@RequestBody String fountainText) throws IOException {
        return fountainService.createPdf(fountainText);
    }
}
