package io.github.TessTheCoder.ChikluAerospace.service;

import io.github.TessTheCoder.ChikluAerospace.dto.EmailRequest;
import io.github.TessTheCoder.ChikluAerospace.dto.NeoResponse;
import io.github.TessTheCoder.ChikluAerospace.dto.PictureResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class NasaService {
    private final RestTemplate restTemplate;
    private final SpringTemplateEngine templateEngine;
    private static final Logger logger = LoggerFactory.getLogger(NasaService.class);

    private static final String NASA_APOD_URL = "planetary/apod";
    private static final String NASA_NEO_URL = "neo/rest/v1/feed";
    public static final String API_KEY = "api_key";

    private final String nasaBaseUrl;
    private final String apiKey;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.sender}")
    private String senderEmail;

    @Autowired
    public NasaService(RestTemplate restTemplate, SpringTemplateEngine templateEngine, JavaMailSender mailSender,
                       @Value("${nasa.api.base}") String nasaBaseUrl,
                       @Value("${nasa.api.key}") String apiKey) {
        this.templateEngine = templateEngine;
        this.nasaBaseUrl = nasaBaseUrl;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
        this.mailSender = mailSender;
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
        return restTemplate.getForObject(url, NeoResponse.class);
    }

    private static String getDateIfRequired(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String sendEmail(EmailRequest emailRequest) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(emailRequest.getReceiverEmail());
            helper.setSubject("Welcome to Chiklu Aerospace");
            helper.setFrom(senderEmail);

            // Create the email body using Thymeleaf
            Context context = new Context();
            context.setVariable("name", emailRequest.getReceiverEmail());
            String htmlContent = templateEngine.process("welcome-email", context);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            logger.info("Email sent successfully to {}", emailRequest.getReceiverEmail());
            return "Email sent successfully!";
        } catch (MailException | MessagingException e) {
            logger.error("Error while sending email: {}", e.getMessage());
            return "Failed to send email!";
        }
    }
}
