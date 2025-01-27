package io.github.TessTheCoder.ChikluAerospace.service;

import io.github.TessTheCoder.ChikluAerospace.dto.NeoResponse;
import io.github.TessTheCoder.ChikluAerospace.dto.PictureResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NasaService {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NasaService.class);

    private static final String NASA_APOD_URL = "planetary/apod";
    private static final String NASA_NEO_URL = "neo/rest/v1/feed";
    public static final String API_KEY = "api_key";
    @Value("${nasa.api.base}")
    private String nasaBaseUrl;

    @Value("${nasa.api.key}")
    private String apiKey;


    public NasaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch the Astronomy Picture of the Day
    public PictureResponse getAstronomyPictureOfTheDay(LocalDate date) {
        logger.info("Getting Astronomy Picture of the Day, date: {}", date);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(nasaBaseUrl)
                .path(NASA_APOD_URL)
                .queryParam(API_KEY, apiKey)
                .queryParam("date", getDateIfRequired(date));

        String url = uriComponentsBuilder.toUriString();

        PictureResponse response = restTemplate.getForObject(url, PictureResponse.class);
        logger.debug("Received NASA response: {}", response);
        return response;
    }

    // Fetch the Near-Earth Objects data
    public NeoResponse getNearEarthObjects(LocalDate date) {
        String formattedDate = getDateIfRequired(date);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(nasaBaseUrl)
                .path(NASA_NEO_URL)
                .queryParam(API_KEY, apiKey)
                .queryParam("start_date", formattedDate)
                .queryParam("end_date", formattedDate);

        String url = uriComponentsBuilder.toUriString();
        NeoResponse response = restTemplate.getForObject(url, NeoResponse.class);
        return response;
    }

    private static String getDateIfRequired(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
