package zti.projekt_zti.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import zti.projekt_zti.dto.StatisticsDto;
import zti.projekt_zti.dto.WellBeeingDto;
import zti.projekt_zti.dto.WellBeeingParameterStatisticsDto;
import zti.projekt_zti.dto.WellBeeingStatisticsDto;
import zti.projekt_zti.service.StatisticsService;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Ta klasa zawiera testy jednostkowe dla klasy {@link StatisticsController} w pakiecie {@code zti.projekt_zti.controller}.
 * Testy te weryfikują poprawność działania kontrolera w obsłudze operacji dotyczących statystyk.
 */
@RunWith(MockitoJUnitRunner.class)
public class StatisticsControllerTest {
    /**
     * Serwis statystyk (mock).
     */
    @Mock
    private StatisticsService statisticsService;

    /**
     * Kontroler, który jest testowany.
     */
    @InjectMocks
    private StatisticsController statisticsController;

    /**
     * MockMvc używane do wykonywania żądań HTTP.
     */
    private MockMvc mockMvc;

    /**
     * Konfiguracja przed wykonaniem testu.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build();
    }

    /**
     * Przypadek testowy pobierania statystyk dotyczących samopoczucia z poprawnymi parametrami.
     * Oczekuje się zwrócenia obiektu typu {@link WellBeeingStatisticsDto}.
     * Mock {@link statisticsService.getWellBeeing()} zwraca ten obiekt.
     * Test sprawdza, czy odpowiedź ma status HTTP 200 OK i czy zawiera oczekiwane dane dotyczące statystyk.
     */
    @Test
    public void testGetWellBeeing_WithValidParameters_ReturnsWellBeeingStatisticsDto() throws Exception {
        WellBeeingStatisticsDto responseDto = new WellBeeingStatisticsDto(Arrays.asList(
                new WellBeeingDto(10, 5),
                new WellBeeingDto(1, 5),
                new WellBeeingDto(10, 5)
        ));
        when(statisticsService.getWellBeeing("week", 1)).thenReturn(responseDto);

        mockMvc.perform(get("/wellBeeing")
                        .param("period", "week")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statistics[0]").value(new WellBeeingDto(10, 5)))
                .andExpect(jsonPath("$.statistics[1]").value(new WellBeeingDto(1, 5)));
    }

    /**
     * Przypadek testowy pobierania statystyk dotyczących samopoczucia z brakującymi parametrami.
     * Oczekuje się zwrócenia odpowiedzi z kodem HTTP 400 Bad Request.
     * Test sprawdza, czy odpowiedź ma oczekiwany kod HTTP oraz czy zawiera oczekiwany komunikat.
     */
    @Test
    public void testGetWellBeeing_WithMissingParameters_ReturnsBadRequest() {
        ResponseEntity<?> response = statisticsController.getWellBeeing(null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Wymagane parametry nie zostały podane.", response.getBody());
    }

    /**
     * Przypadek testowy pobierania statystyk dotyczących parametru samopoczucia z poprawnymi parametrami.
     * Oczekuje się zwrócenia obiektu typu {@link WellBeeingParameterStatisticsDto}.
     * Mock {@link statisticsService.getWellBeeingParameter()} zwraca ten obiekt.
     * Test sprawdza, czy odpowiedź ma status HTTP 200 OK i czy zawiera oczekiwane dane dotyczące statystyk parametru samopoczucia.
     */
    @Test
    public void testGetWellBeeingParameter_WithValidParameters_ReturnsWellBeeingParameterStatisticsDto() {
        WellBeeingParameterStatisticsDto wellBeeingParameterStatisticsDto = new WellBeeingParameterStatisticsDto();
        when(statisticsService.getWellBeeingParameter(anyString(), anyInt(), anyString(), anyString(), anyString()))
                .thenReturn(wellBeeingParameterStatisticsDto);

        ResponseEntity<?> response = statisticsController.getWellBeeingParameter("week", 1, "physical",
                "workH", "Supplement A");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(wellBeeingParameterStatisticsDto, response.getBody());
    }

    /**
     * Przypadek testowy pobierania statystyk dotyczących parametru samopoczucia z brakującymi parametrami.
     * Oczekuje się zwrócenia odpowiedzi z kodem HTTP 400 Bad Request.
     * Test sprawdza, czy odpowiedź ma oczekiwany kod HTTP oraz czy zawiera oczekiwany komunikat.
     */
    @Test
    public void testGetWellBeeingParameter_WithMissingParameters_ReturnsBadRequest() {
        ResponseEntity<?> response = statisticsController.getWellBeeingParameter(null, null, null,
                null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Wymagane parametry nie zostały podane.", response.getBody());
    }

    /**
     * Przypadek testowy pobierania ogólnych statystyk z poprawnymi parametrami.
     * Oczekuje się zwrócenia obiektu typu {@link StatisticsDto}.
     * Mock {@link statisticsService.getStatistics()} zwraca ten obiekt.
     * Test sprawdza, czy odpowiedź ma status HTTP 200 OK i czy zawiera oczekiwane dane dotyczące ogólnych statystyk.
     */
    @Test
    public void testGetStatistics_WithValidParameters_ReturnsStatisticsDto() {
        StatisticsDto statisticsDto = new StatisticsDto();
        when(statisticsService.getStatistics(anyString(), anyInt(), anyString(), anyString()))
                .thenReturn(statisticsDto);

        ResponseEntity<?> response = statisticsController.getStatistics("week", 1, "parameter", "name");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(statisticsDto, response.getBody());
    }

    /**
     * Przypadek testowy pobierania ogólnych statystyk z brakującymi parametrami.
     * Oczekuje się zwrócenia odpowiedzi z kodem HTTP 400 Bad Request.
     * Test sprawdza, czy odpowiedź ma oczekiwany kod HTTP oraz czy zawiera oczekiwany komunikat.
     */
    @Test
    public void testGetStatistics_WithMissingParameters_ReturnsBadRequest() {
        ResponseEntity<?> response = statisticsController.getStatistics(null, null, null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Wymagane parametry nie zostały podane.", response.getBody());
    }
}
