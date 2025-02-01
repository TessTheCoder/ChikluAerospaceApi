package io.github.TessTheCoder.ChikluAerospace.controller;

import io.github.TessTheCoder.ChikluAerospace.dto.EmailRequest;
import io.github.TessTheCoder.ChikluAerospace.dto.NeoResponse;
import io.github.TessTheCoder.ChikluAerospace.dto.PictureResponse;
import io.github.TessTheCoder.ChikluAerospace.service.NasaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class NasaController {
    private static final Logger logger = LoggerFactory.getLogger(NasaController.class);
    private static final String PICTURE = "picture";
    private final NasaService nasaService;

    @Autowired
    public NasaController(NasaService nasaService) {
        this.nasaService = nasaService;
    }

    @GetMapping("/picture")
    public ModelAndView getAstronomyPictureOfTheDay(@RequestParam(required = false) LocalDate date, Model model) {
        PictureResponse astronomyPictureOfTheDay = nasaService.getAstronomyPictureOfTheDay(date);
        model.addAttribute(PICTURE, astronomyPictureOfTheDay);
        return new ModelAndView(PICTURE);
    }

    @GetMapping("/neo")
    public ModelAndView getNearEarthObjects(@RequestParam(required = false) LocalDate date, Model model) {
        NeoResponse nearEarthObjects = nasaService.getNearEarthObjects(date);
        model.addAttribute("neoResponse", nearEarthObjects);
        return new ModelAndView("neo");
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        String response = nasaService.sendEmail(emailRequest);
        return ResponseEntity.ok(response);
    }
}
