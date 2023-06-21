package zti.projekt_zti.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import zti.projekt_zti.dto.ParameterStatisticsDto;
import zti.projekt_zti.dto.StatisticsDto;
import zti.projekt_zti.dto.WellBeeingParameterStatisticsDto;
import zti.projekt_zti.dto.WellBeeingStatisticsDto;
import zti.projekt_zti.entity.Day;
import zti.projekt_zti.repository.DayRepository;
import zti.projekt_zti.repository.DrugRepository;
import zti.projekt_zti.repository.MeetingRepository;
import zti.projekt_zti.repository.SupplementRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Ta klasa zawiera testy jednostkowe dla klasy {@link StatisticsService} w pakiecie {@code zti.projekt_zti.service}.
 * Testy te weryfikują poprawność działania serwisu w obsłudze operacji dotyczących dni.
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {
    @Mock
    private DayRepository dayRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Przypadek testowy pobierania statystyk dobre samopoczucie - tydzień.
     * Test sprawdza, czy metoda {@link statisticsService.getWellBeeing()} poprawnie zwraca statystyki dotyczące dobre samopoczucie na przestrzeni tygodnia.
     */

    @Test
    public void testGetWellBeeing_Week() {
        Integer userId = 1;
        String period = "week";

        LocalDate endDate = LocalDate.of(2023, 6, 19);
        LocalDateTime localDateTimeWithMidnightEnd = endDate.atStartOfDay();
        Timestamp timestampEnd = Timestamp.valueOf(localDateTimeWithMidnightEnd);

        LocalDate startDate = endDate.minusDays(7);
        LocalDateTime localDateTimeWithMidnightStart = startDate.atStartOfDay();
        Timestamp timestampStart = Timestamp.valueOf(localDateTimeWithMidnightStart);

        List<Day> days = new ArrayList<>();
        Day day1 = new Day();
        day1.setPhysicalWellBeeing(5);
        day1.setMentalWellBeeing(4);
        days.add(day1);

        Day day2 = new Day();
        day2.setPhysicalWellBeeing(3);
        day2.setMentalWellBeeing(2);
        days.add(day2);

        when(dayRepository.findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId)).thenReturn(days);

        WellBeeingStatisticsDto result = statisticsService.getWellBeeing(period, userId);

        assertEquals(2, result.getStatistics().size());
        assertEquals(5, result.getStatistics().get(0).getX());
        assertEquals(4, result.getStatistics().get(0).getY());
        assertEquals(3, result.getStatistics().get(1).getX());
        assertEquals(2, result.getStatistics().get(1).getY());
    }

    /**
     * Przypadek testowy pobierania statystyk określonego parametru dobre samopoczucie - miesiąc.
     * Test sprawdza, czy metoda {@link statisticsService.getWellBeeingParameter()} poprawnie zwraca statystyki określonego parametru dobre samopoczucie na przestrzeni miesiąca.
     */
    @Test
    public void testGetWellBeeingParameter_Month() {
        Integer userId = 1;
        String period = "month";
        String wellBeeing = "physical";
        String parameter = "workH";
        String name = "";

        LocalDate endDate = LocalDate.of(2023, 6, 19);
        LocalDateTime localDateTimeWithMidnightEnd = endDate.atStartOfDay();
        Timestamp timestampEnd = Timestamp.valueOf(localDateTimeWithMidnightEnd);

        LocalDate startDate = endDate.minusDays(30);
        LocalDateTime localDateTimeWithMidnightStart = startDate.atStartOfDay();
        Timestamp timestampStart = Timestamp.valueOf(localDateTimeWithMidnightStart);

        List<Day> days = new ArrayList<>();
        Day day1 = new Day();
        day1.setWorkH(8.0);
        day1.setPhysicalWellBeeing(5);
        days.add(day1);

        Day day2 = new Day();
        day2.setWorkH(7.5);
        day2.setPhysicalWellBeeing(4);
        days.add(day2);

        when(dayRepository.findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId)).thenReturn(days);

        WellBeeingParameterStatisticsDto result = statisticsService.getWellBeeingParameter(period, userId, wellBeeing, parameter, name);

        assertEquals(2, result.getStatistics().size());
        assertEquals(8.0, result.getStatistics().get(0).getX());
        assertEquals(5, result.getStatistics().get(0).getY());
        assertEquals(7.5, result.getStatistics().get(1).getX());
        assertEquals(4, result.getStatistics().get(1).getY());
    }
    /**
     * Przypadek testowy pobierania statystyk określonego parametru.
     * Test sprawdza, czy metoda {@link statisticsService.getParameter()} poprawnie zwraca statystyki określonego parametru na przestrzeni tygodnia.
     */
    @Test
    public void testGetParameter() {
        String period = "week";
        Integer userId = 1;
        String parameter = "workH";
        LocalDate endDate = LocalDate.now();
        LocalDateTime localDateTimeWithMidnightEnd = endDate.atStartOfDay();
        Timestamp timestampEnd = Timestamp.valueOf(localDateTimeWithMidnightEnd);
        LocalDate startDate = endDate.minusDays(7);
        LocalDateTime localDateTimeWithMidnightStart = startDate.atStartOfDay();
        Timestamp timestampStart = Timestamp.valueOf(localDateTimeWithMidnightStart);

        List<Day> days = new ArrayList<>();
        days.add(new Day());

        when(dayRepository.findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId)).thenReturn(days);

        ParameterStatisticsDto result = statisticsService.getParameter(period, userId, parameter, null);

        Assertions.assertNotNull(result);

        verify(dayRepository, times(1)).findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId);
    }

    /**
     * Przypadek testowy pobierania ogólnych statystyk.
     * Test sprawdza, czy metoda {@link statisticsService.getStatistics()} poprawnie zwraca ogólne statystyki na przestrzeni tygodnia.
     */
    @Test
    public void testGetStatistics() {
        String period = "week";
        Integer userId = 1;
        String parameter = "workH";
        String name = "Work";
        LocalDate endDate = LocalDate.now();
        LocalDateTime localDateTimeWithMidnightEnd = endDate.atStartOfDay();
        Timestamp timestampEnd = Timestamp.valueOf(localDateTimeWithMidnightEnd);
        LocalDate startDate = endDate.minusDays(7);
        LocalDateTime localDateTimeWithMidnightStart = startDate.atStartOfDay();
        Timestamp timestampStart = Timestamp.valueOf(localDateTimeWithMidnightStart);

        List<Day> days = new ArrayList<>();
        days.add(new Day());

        when(dayRepository.findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId)).thenReturn(days);

        StatisticsDto result = statisticsService.getStatistics(period, userId, parameter, name);

        Assertions.assertNotNull(result);

        verify(dayRepository, times(1)).findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId);
    }
}
