package zti.projekt_zti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import zti.projekt_zti.dto.*;
import zti.projekt_zti.service.DayService;

import java.text.SimpleDateFormat;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Klasa zawierająca testy jednostkowe dla kontrolera DayController.
 */
@RunWith(MockitoJUnitRunner.class)
public class DayControllerTest {

    /**
     * Serwis dnia (mock).
     */
    @Mock
    private DayService dayService;

    /**
     * Kontroler, który jest testowany.
     */
    @InjectMocks
    private DayController dayController;

    /**
     * MockMvc używane do wykonywania żądań HTTP.
     */
    private MockMvc mockMvc;

    /**
     * Konfiguracja przed wykonaniem testu.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(dayController).build();
    }

    /**
     * Testuje poprawne pobieranie dnia.
     *
     * <p>Opis testu:
     * Sprawdza, czy zwracany jest poprawny dzień na podstawie żądania zawierającego prawidłową datę i identyfikator użytkownika.
     * Tworzy obiekt GetDayRequestDto z żądanymi danymi.
     * Tworzy obiekt GetDayResponseDto z oczekiwanymi danymi dla zwróconego dnia.
     * Konfiguruje zachowanie mocka dayService.getDay() dla żądania z odpowiednimi danymi.
     * Wykonuje żądanie GET na punkt końcowy "/day" z danymi żądania.
     * Sprawdza, czy odpowiedź ma status HTTP 200 OK.
     * Sprawdza, czy odpowiedź zawiera poprawne dane dla zwróconego dnia.
     */
    @Test
    public void testGetDay_ValidRequest_ReturnsOk() throws Exception {
        GetDayRequestDto requestDto = new GetDayRequestDto(new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-19"), 1);
        GetDayResponseDto responseDto = new GetDayResponseDto("2023-06-19", 1);
        Mockito.when(dayService.getDay(requestDto)).thenReturn(responseDto);

        mockMvc.perform(get("/day")
                        .param("date", "2023-06-19")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2023-06-19"))
                .andExpect(jsonPath("$.userId").value(1));
    }

    /**
     * Testuje żądanie pobrania dnia z brakującymi parametrami.
     *
     * <p>Opis testu:
     * Sprawdza, czy żądanie GET na punkt końcowy "/day" bez wymaganych parametrów zwraca status HTTP 400 Bad Request.
     */
    @Test
    public void testGetDay_MissingParameters_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/day"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testuje poprawne aktualizowanie dnia.
     *
     * <p>Opis testu:
     * Sprawdza, czy dane dnia są poprawnie zaktualizowane przez żądanie POST zawierające odpowiednie dane.
     * Tworzy obiekt UpdateDayDto z danymi żądania.
     * Wykonuje żądanie POST na punkt końcowy "/updateDay" z danymi żądania w formacie JSON.
     * Sprawdza, czy odpowiedź ma status HTTP 200 OK.
     * Sprawdza, czy odpowiedź zawiera oczekiwaną wiadomość potwierdzającą zaktualizowanie danych.
     */
    @Test
    public void testUpdateDay_ValidRequest_ReturnsOk() throws Exception {
        UpdateDayDto requestDto = new UpdateDayDto();
        requestDto.setUserId(1);
        requestDto.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-19"));

        mockMvc.perform(post("/updateDay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Zaktualizowano dane"));
    }

    /**
     * Testuje żądanie aktualizacji dnia z brakującymi parametrami.
     *
     * <p>Opis testu:
     * Sprawdza, czy żądanie POST na punkt końcowy "/updateDay" bez wymaganych parametrów zwraca status HTTP 400 Bad Request.
     */
    @Test
    public void testUpdateDay_MissingParameters_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/updateDay"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testuje poprawne pobieranie wszystkich dni.
     *
     * <p>Opis testu:
     * Sprawdza, czy zwracane są poprawne dni dla danego użytkownika na podstawie żądania zawierającego identyfikator użytkownika.
     * Tworzy obiekt DaysDto z oczekiwanymi danymi dla zwróconych dni.
     * Konfiguruje zachowanie mocka dayService.getAllDays() dla żądania z odpowiednimi danymi.
     * Wykonuje żądanie GET na punkt końcowy "/days" z danymi żądania.
     * Sprawdza, czy odpowiedź ma status HTTP 200 OK.
     * Sprawdza, czy odpowiedź zawiera poprawne dane dla zwróconych dni.
     */
    @Test
    public void testGetAllDays_ValidRequest_ReturnsOk() throws Exception {
        DaysDto responseDto = new DaysDto(Collections.singletonList(new DayInfoDto(1L, "2023-06-19", 1, 10)));
        Mockito.when(dayService.getAllDays(1)).thenReturn(responseDto);

        mockMvc.perform(get("/days")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.days[0].id").value(1))
                .andExpect(jsonPath("$.days[0].date").value("2023-06-19"))
                .andExpect(jsonPath("$.days[0].physicalWellBeeing").value("1"))
                .andExpect(jsonPath("$.days[0].mentalWellBeeing").value("10"));
    }

    /**
     * Testuje żądanie pobrania wszystkich dni z brakującymi parametrami.
     *
     * <p>Opis testu:
     * Sprawdza, czy żądanie GET na punkt końcowy "/days" bez wymaganych parametrów zwraca status HTTP 400 Bad Request.
     */
    @Test
    public void testGetAllDays_MissingParameters_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/days"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testuje poprawne usuwanie dnia.
     *
     * <p>Opis testu:
     * Sprawdza, czy żądanie DELETE usuwa odpowiedni dzień na podstawie daty i identyfikatora użytkownika.
     * Wykonuje żądanie DELETE na punkt końcowy "/deleteDay" z danymi żądania.
     * Sprawdza, czy odpowiedź ma status HTTP 200 OK.
     * Sprawdza, czy odpowiedź zawiera oczekiwaną wiadomość potwierdzającą usunięcie dnia.
     */
    @Test
    public void testDeleteDay_ValidRequest_ReturnsOk() throws Exception {
        mockMvc.perform(delete("/deleteDay")
                .param("date", "2023-06-19")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Usunieto dzien"));
    }

    /**
     * Testuje żądanie usunięcia dnia z brakującymi parametrami.
     *
     * <p>Opis testu:
     * Sprawdza, czy żądanie DELETE na punkt końcowy "/deleteDay" bez wymaganych parametrów zwraca status HTTP 400 Bad Request.
     */
    @Test
    public void testDeleteDay_MissingParameters_ReturnsBadRequest() throws Exception {
        mockMvc.perform(delete("/deleteDay"))
                .andExpect(status().isBadRequest());
    }
}
