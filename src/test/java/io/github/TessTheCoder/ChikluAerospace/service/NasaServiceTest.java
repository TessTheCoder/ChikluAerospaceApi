package io.github.TessTheCoder.ChikluAerospace.service;

    import io.github.TessTheCoder.ChikluAerospace.dto.NeoResponse;
    import io.github.TessTheCoder.ChikluAerospace.dto.PictureResponse;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;
    import org.springframework.web.client.RestTemplate;

    import java.time.LocalDate;

    import static org.junit.jupiter.api.Assertions.assertNotNull;
    import static org.mockito.ArgumentMatchers.anyString;
    import static org.mockito.Mockito.*;

    class NasaServiceTest {

        @Mock
        private RestTemplate restTemplate;

        @InjectMocks
        private NasaService nasaService;

        private String nasaBaseUrl="https://api.nasa.gov/";

        private String apiKey="DEMO_KEY";

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            nasaService = new NasaService(restTemplate, nasaBaseUrl, apiKey);
        }

        @Test
        void getAstronomyPictureOfTheDay() {
            PictureResponse mockResponse = new PictureResponse();
            when(restTemplate.getForObject(anyString(), eq(PictureResponse.class))).thenReturn(mockResponse);

            PictureResponse response = nasaService.getAstronomyPictureOfTheDay(LocalDate.of(2023, 10, 1));

            assertNotNull(response);
            verify(restTemplate, times(1)).getForObject(anyString(), eq(PictureResponse.class));
        }

        @Test
        void getNearEarthObjects() {
            NeoResponse mockResponse = new NeoResponse();
            when(restTemplate.getForObject(anyString(), eq(NeoResponse.class))).thenReturn(mockResponse);

            NeoResponse response = nasaService.getNearEarthObjects(LocalDate.of(2023, 10, 1));

            assertNotNull(response);
            verify(restTemplate, times(1)).getForObject(anyString(), eq(NeoResponse.class));
        }

        @Test
        void getAstronomyPictureOfTheDayWithNullDate() {
            PictureResponse mockResponse = new PictureResponse();
            when(restTemplate.getForObject(anyString(), eq(PictureResponse.class))).thenReturn(mockResponse);

            PictureResponse response = nasaService.getAstronomyPictureOfTheDay(null);

            assertNotNull(response);
            verify(restTemplate, times(1)).getForObject(anyString(), eq(PictureResponse.class));
        }
    }