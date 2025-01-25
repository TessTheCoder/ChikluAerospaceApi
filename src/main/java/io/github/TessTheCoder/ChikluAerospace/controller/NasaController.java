package io.github.TessTheCoder.ChikluAerospace.controller;

import io.github.TessTheCoder.ChikluAerospace.dto.NeoResponse;
import io.github.TessTheCoder.ChikluAerospace.dto.PictureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
class NasaController {
    public static final String PICTURE = "picture";
    public static final String API_KEY = "api_key";
    private final RestTemplate restTemplate;

    public NasaController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Value("${nasa.api.base}")
    private String nasaBaseUrl;

    private static final String NASA_APOD_URL = "planetary/apod";
    private static final String NASA_NEO_URL = "neo/rest/v1/feed";

    @Value("${nasa.api.key}")
    private String apiKey;

    @GetMapping("/picture")
    public ModelAndView getAstronomyPictureOfTheDay(@RequestParam(required = false) LocalDate date, Model model) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(nasaBaseUrl)
                .path(NASA_APOD_URL)
                .queryParam(API_KEY, apiKey)
                .queryParam("date", getDateIfRequired(date));

        String url = uriComponentsBuilder.toUriString();

        PictureResponse response = restTemplate.getForObject(url, PictureResponse.class);
        model.addAttribute(PICTURE, response);
        return new ModelAndView(PICTURE);
    }

    @GetMapping("/neo")
    public ModelAndView getNearEarthObjects(@RequestParam(required = false) LocalDate date, Model model) {
        String formattedDate = getDateIfRequired(date);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(nasaBaseUrl)
                .path(NASA_NEO_URL)
                .queryParam(API_KEY, apiKey)
                .queryParam("start_date", formattedDate)
                .queryParam("end_date", formattedDate);

        String url = uriComponentsBuilder.toUriString();
        NeoResponse response = restTemplate.getForObject(url, NeoResponse.class);
        model.addAttribute("neoResponse", response);
        return new ModelAndView("neo");
    }

    private static String getDateIfRequired(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}