package zti.projekt_zti.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import zti.projekt_zti.dto.*;
import zti.projekt_zti.entity.Day;
import zti.projekt_zti.entity.Drug;
import zti.projekt_zti.entity.Meeting;
import zti.projekt_zti.entity.Supplement;
import zti.projekt_zti.repository.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Ta klasa zawiera testy jednostkowe dla klasy DayService w pakiecie {@code zti.projekt_zti.service}.
 * Testy te weryfikują poprawność działania serwisu w obsłudze operacji dotyczących dni.
 */
public class DayServiceTest {

    @Mock
    private DayRepository dayRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private SupplementRepository supplementRepository;

    @Mock
    private DrugRepository drugRepository;

    @Mock
    private Supplement2Repository supplement2Repository;

    @Mock
    private Meeting2Repository meeting2Repository;

    @Mock
    private Drug2Repository drug2Repository;

    @Mock
    private Day2Repository day2Repository;

    @InjectMocks
    private DayService dayService;

    @Captor
    private ArgumentCaptor<Timestamp> timestampCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Przypadek testowy pobierania istniejącego dnia.
     * Oczekuje się zwrócenia obiektu typu GetDayResponseDto zawierającego odpowiednie dane.
     * Mock dayRepository.findByDateAndUserId() zwraca istniejący dzień.
     * Test sprawdza, czy zwrócone dane są zgodne z oczekiwaniami.
     */
    @Test
    public void testGetDay_ExistingDay() throws ParseException {
        GetDayRequestDto request = new GetDayRequestDto();
        request.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-09-09"));
        request.setUserId(1);

        Day day = new Day();
        day.setId(1L);
        day.setUserId(request.getUserId());
        day.setDate(request.getDate());

        Meeting meeting = new Meeting();
        meeting.setMeetingH(2.0);
        meeting.setPeopleCount(3);
        meeting.setDayId(day.getId());

        List<Drug> drugs = new ArrayList<>();
        Drug drug = new Drug();
        drug.setDrugName("Drug 1");
        drug.setDrugAmount(1.0);
        drug.setDayId(day.getId());
        drugs.add(drug);

        List<Supplement> supplements = new ArrayList<>();
        Supplement supplement = new Supplement();
        supplement.setSupplementName("Supplement 1");
        supplement.setSupplementAmount(1.0);
        supplement.setDayId(day.getId());
        supplements.add(supplement);

        when(dayRepository.findByDateAndUserId(any(Timestamp.class), anyInt())).thenReturn(day);
        when(meetingRepository.findByDayId(eq(day.getId()))).thenReturn(meeting);
        when(drugRepository.findByDayId(eq(day.getId()))).thenReturn(drugs);
        when(supplementRepository.findByDayId(eq(day.getId()))).thenReturn(supplements);

        GetDayResponseDto response = dayService.getDay(request);

        verify(dayRepository).findByDateAndUserId(timestampCaptor.capture(), eq(request.getUserId()));
        Assertions.assertEquals(request.getUserId(), day.getUserId());
        Assertions.assertEquals("2022-09-09", response.getDate());
        Assertions.assertEquals(supplements.size(), response.getSupplements().size());
        Assertions.assertEquals(meeting.getMeetingH(), response.getMeeting().getMeetingH());
        Assertions.assertEquals(drugs.size(), response.getDrugs().size());
    }

    /**
     * Przypadek testowy pobierania nieistniejącego dnia.
     * Oczekuje się zwrócenia pustego obiektu typu GetDayResponseDto.
     * Mock dayRepository.findByDateAndUserId() zwraca wartość null.
     * Test sprawdza, czy zwrócone dane są zgodne z oczekiwaniami.
     */
    @Test
    public void testGetDay_NonExistingDay() {
        GetDayRequestDto request = new GetDayRequestDto();
        request.setDate(new Date());
        request.setUserId(1);

        when(dayRepository.findByDateAndUserId(any(Timestamp.class), anyInt())).thenReturn(null);

        GetDayResponseDto response = dayService.getDay(request);

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = today.format(formatter);

        verify(dayRepository).findByDateAndUserId(timestampCaptor.capture(), eq(request.getUserId()));
        Assertions.assertEquals(formattedDate,response.getDate());
        Assertions.assertNull(response.getSupplements());
        Assertions.assertNull(response.getMeeting());
        Assertions.assertNull(response.getDrugs());
    }

    /**
     * Przypadek testowy aktualizacji istniejącego dnia.
     * Test sprawdza, czy metoda dayService.updateDay() aktualizuje istniejący dzień zgodnie z przekazanymi danymi.
     */
    @Test
    public void testUpdateDay_ExistingDay() {
        UpdateDayDto request = new UpdateDayDto();
        request.setDate(new Date());
        request.setMeeting(new MeetingDto());
        request.setSupplements(new ArrayList<>());
        request.setDrugs(new ArrayList<>());

        Day existingDay = new Day();
        existingDay.setId(1L);
        existingDay.setUserId(1);

        MockitoAnnotations.initMocks(this);

        when(dayRepository.findByDateAndUserId(dayService.getConvertedDay(new Date()),1)).thenReturn(existingDay);
        when(dayRepository.save(any(Day.class))).thenReturn(existingDay);

        Day day = dayRepository.findByDateAndUserId(dayService.getConvertedDay(request.getDate()), 1);
        dayService.updateDay(request);

        Assertions.assertEquals(existingDay, day);
    }

    /**
     * Przypadek testowy pobierania wszystkich dni użytkownika, gdy istnieją dni.
     * Oczekuje się zwrócenia obiektu typu DaysDto zawierającego dane o dniach.
     * Mock dayRepository.findAllByUserId() zwraca listę dni.
     * Test sprawdza, czy zwrócone dane są zgodne z oczekiwaniami.
     */
    @Test
    void testGetAllDays_WithDays() throws ParseException {
        Integer userId = 1;
        Day day1 = new Day();
        day1.setUserId(userId);
        day1.setId(1L);
        day1.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-17"));
        day1.setMentalWellBeeing(6);
        day1.setPhysicalWellBeeing(5);
        Day day2 = new Day();
        day2.setUserId(userId);
        day2.setId(2L);
        day2.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-18"));
        day2.setMentalWellBeeing(7);
        day2.setPhysicalWellBeeing(8);
        List<Day> days = Arrays.asList(
                day1,
                day2
        );
        when(dayRepository.findAllByUserId(userId)).thenReturn(days);

        DaysDto result = dayService.getAllDays(userId);

        Assertions.assertEquals(2, result.getDays().size());
        Assertions.assertEquals(2L, result.getDays().get(0).getId());
        Assertions.assertEquals("2023-06-18", result.getDays().get(0).getDate());
        Assertions.assertEquals(8, result.getDays().get(0).getPhysicalWellBeeing());
        Assertions.assertEquals(7, result.getDays().get(0).getMentalWellBeeing());
        Assertions.assertEquals(1L, result.getDays().get(1).getId());
        Assertions.assertEquals("2023-06-17", result.getDays().get(1).getDate());
        Assertions.assertEquals(5, result.getDays().get(1).getPhysicalWellBeeing());
        Assertions.assertEquals(6, result.getDays().get(1).getMentalWellBeeing());
    }

    /**
     * Przypadek testowy pobierania wszystkich dni użytkownika, gdy nie ma żadnych dni.
     * Oczekuje się zwrócenia pustego obiektu typu DaysDto.
     * Mock dayRepository.findAllByUserId() zwraca wartość null.
     * Test sprawdza, czy zwrócone dane są zgodne z oczekiwaniami.
     */
    @Test
    void testGetAllDays_WithoutDays() {
        Integer userId = 1;
        when(dayRepository.findAllByUserId(userId)).thenReturn(null);

        DaysDto result = dayService.getAllDays(userId);

        assertEquals(0, result.getDays().size());
    }

}


