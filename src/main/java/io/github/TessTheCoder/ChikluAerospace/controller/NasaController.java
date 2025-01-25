package io.github.TessTheCoder.ChikluAerospace.controller;

import io.github.TessTheCoder.ChikluAerospace.dto.NeoResponse;
import io.github.TessTheCoder.ChikluAerospace.dto.PictureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

@Controller
class NasaController {
    private final RestTemplate restTemplate;

    public NasaController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String NASA_APOD_URL = "https://api.nasa.gov/planetary/apod";
    private static final String NASA_NEO_URL = "https://api.nasa.gov/neo/rest/v1/feed";

    @Value("${nasa.api.key}")
    private String API_KEY;

    @GetMapping("/picture")
    public ModelAndView getAstronomyPictureOfTheDay( Model model) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String url = String.format("%s?api_key=%s&date=%s" , NASA_APOD_URL, API_KEY, currentDate);
        PictureResponse response = restTemplate.getForObject(url, PictureResponse.class);
        model.addAttribute("picture", response);
        model.addAttribute("url", response.getUrl());
        return new ModelAndView("picture");
    }

    @GetMapping("/neo")
    public ModelAndView getNearEarthObjects( Model model) {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ISO_DATE);
        String url = String.format("%s?api_key=%s&start_date=%s&end_date=%s", NASA_NEO_URL, API_KEY, formattedDate, formattedDate);
        NeoResponse response = restTemplate.getForObject(url, NeoResponse.class);
        model.addAttribute("neoResponse", response);
      //  model.addAttribute("neo", response.getneo());
        return new ModelAndView("neo");
    }
}