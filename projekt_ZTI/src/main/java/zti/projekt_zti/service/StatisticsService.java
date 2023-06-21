package zti.projekt_zti.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import zti.projekt_zti.dto.*;
import zti.projekt_zti.entity.Day;
import zti.projekt_zti.entity.Drug;
import zti.projekt_zti.entity.Meeting;
import zti.projekt_zti.entity.Supplement;
import zti.projekt_zti.repository.DayRepository;
import zti.projekt_zti.repository.DrugRepository;
import zti.projekt_zti.repository.MeetingRepository;
import zti.projekt_zti.repository.SupplementRepository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Serwis obsługujący operacje związane ze statystykami.
 */
@RequiredArgsConstructor
@Service
public class StatisticsService {
    private final DayRepository dayRepository;
    @Autowired
    private DrugRepository drugRepository;
    @Autowired
    private SupplementRepository supplementRepository;
    @Autowired
    private MeetingRepository meetingRepository;

    /**
     * Pobiera statystyki dotyczące samopoczucia.
     *
     * @param period  okres, dla którego mają zostać pobrane statystyki ("week", "month", "year")
     * @param userId  identyfikator użytkownika
     * @return        obiekt typu WellBeeingStatisticsDto zawierający statystyki samopoczucia
     */
    public WellBeeingStatisticsDto getWellBeeing(String period, Integer userId)
    {
        LocalDate endDate = LocalDate.now();
        LocalDateTime localDateTimeWithMidnightEnd = endDate.atStartOfDay();
        Timestamp timestampEnd= Timestamp.valueOf(localDateTimeWithMidnightEnd);

        LocalDate startDate = endDate;

        switch(period)
        {
            case "week":
                startDate = endDate.minusDays(7);
                break;
            case "month":
                startDate = endDate.minusDays(30);
                break;
            case "year":
                startDate = endDate.minusDays(365);
                break;
        }
        LocalDateTime localDateTimeWithMidnightStart = startDate.atStartOfDay();
        Timestamp timestampStart= Timestamp.valueOf(localDateTimeWithMidnightStart);

        List<Day> days = dayRepository.findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId);
        if(days != null)
        {
            List<WellBeeingDto> daysToResponse = new ArrayList<WellBeeingDto>();
            for(Day day : days)
            {
                daysToResponse.add(new WellBeeingDto(day.getPhysicalWellBeeing(), day.getMentalWellBeeing()));
            }
            return new WellBeeingStatisticsDto(daysToResponse);
        }
        return new WellBeeingStatisticsDto();
    }

    /**
     * Pobiera statystyki dotyczące parametru samopoczucia.
     *
     * @param period      okres, dla którego mają zostać pobrane statystyki ("week", "month", "year")
     * @param userId      identyfikator użytkownika
     * @param wellBeeing  rodzaj samopoczucia ("physical" lub "mental")
     * @param parameter   parametr dotyczący samopoczucia
     * @param name        nazwa parametru
     * @return            obiekt typu WellBeeingParameterStatisticsDto zawierający statystyki parametru samopoczucia
     */
    public WellBeeingParameterStatisticsDto getWellBeeingParameter(String period, Integer userId, String wellBeeing, String parameter, String name)
    {
        LocalDate endDate = LocalDate.now();
        LocalDateTime localDateTimeWithMidnightEnd = endDate.atStartOfDay();
        Timestamp timestampEnd= Timestamp.valueOf(localDateTimeWithMidnightEnd);

        LocalDate startDate = endDate;

        switch(period)
        {
            case "week":
                startDate = endDate.minusDays(7);
                break;
            case "month":
                startDate = endDate.minusDays(30);
                break;
            case "year":
                startDate = endDate.minusDays(365);
                break;
        }
        LocalDateTime localDateTimeWithMidnightStart = startDate.atStartOfDay();
        Timestamp timestampStart= Timestamp.valueOf(localDateTimeWithMidnightStart);

        List<Day> days = dayRepository.findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId);

        if(days != null)
        {
            List<WellBeeingParameterDto> daysToResponse = new ArrayList<WellBeeingParameterDto>();
            String parameterName = "";

            for(Day day : days)
            {
                Double parameterValue = 0.0;
                switch (parameter){
                    case "workH":
                        parameterValue = day.getWorkH() != null ? day.getWorkH() : 0.0;
                        parameterName = "Przepracowane godziny";
                        break;
                    case "studyH":
                        parameterValue = day.getStudiesH() != null ? day.getStudiesH() : 0.0;
                        parameterName = "Godziny na uczelni";
                        break;
                    case "learnH":
                        parameterValue = day.getLearningH() != null ? day.getLearningH() : 0.0;
                        parameterName = "Godziny nauki";
                        break;
                    case "sleepH":
                        parameterValue = day.getSleepH() != null ? day.getSleepH() : 0.0;
                        parameterName = "Przespane godziny";
                        break;
                    case "sportH":
                        parameterValue = day.getSportH() != null ? day.getSportH() : 0.0;
                        parameterName = "Godziny spędzone na sporcie";
                        break;
                    case "restH":
                        parameterValue = day.getRestH() != null ? day.getRestH() : 0.0;
                        parameterName = "Godziny odpoczynku";
                        break;
                    case "mealCount":
                        parameterValue = Double.valueOf(day.getMealsNumber()!= null ? day.getMealsNumber() : 0.0);
                        parameterName = "Liczba posiłków";
                        break;
                    case "waterCount":
                        parameterValue = day.getWater() != null ? day.getWater() : 0.0;
                        parameterName = "Ilość wypitej wody";
                        break;
                    case "drugs":
                        List<Drug> drugs = drugRepository.findByDayIdAndDrugName(day.getId(), name);
                        parameterValue = 0.0;
                        if(drugs != null)
                        {
                            for (Drug drug : drugs) {
                                parameterValue += drug.getDrugAmount() != null ? drug.getDrugAmount() : 0.0;
                            }
                        }
                        parameterName = name;
                        break;
                    case "supplements":
                        List<Supplement> supplements = supplementRepository.findByDayIdAndSupplementName(day.getId(), name);
                        parameterValue = 0.0;
                        if(supplements != null)
                        {
                            for (Supplement supplement : supplements) {
                                parameterValue += supplement.getSupplementAmount() != null ? supplement.getSupplementAmount() : 0.0;
                            }
                        }
                        parameterName = name;
                        break;
                    case "meetingH":
                        Meeting meeting = meetingRepository.findByDayId(day.getId());
                        parameterValue = meeting != null ? meeting.getMeetingH() : 0.0;
                        parameterName = "Ilość czasu na spotkaniach towarzyskich";
                        break;
                    case "peopleCount":
                        Meeting meetingPeople = meetingRepository.findByDayId(day.getId());
                        parameterValue = meetingPeople != null ? meetingPeople.getPeopleCount().doubleValue() : 0.0;
                        parameterName = "Ilość spotkanych osób";
                        break;

                }
                if (parameterValue != 0.0)
                {
                    daysToResponse.add(new WellBeeingParameterDto(parameterValue, Objects.equals(wellBeeing, "physical") ? day.getPhysicalWellBeeing() : day.getMentalWellBeeing()));
                }
            }
            return new WellBeeingParameterStatisticsDto(daysToResponse, parameterName, Objects.equals(wellBeeing, "physical") ? "Samopoczucie fizyczne" : "Samopoczucie psychiczne");
        }
        return new WellBeeingParameterStatisticsDto();
    }

    /**
     * Pobiera statystyki dotyczące parametru.
     *
     * @param period      okres, dla którego mają zostać pobrane statystyki ("week", "month", "year")
     * @param userId      identyfikator użytkownika
     * @param parameter   parametr
     * @param name        nazwa parametru
     * @return            obiekt typu ParameterStatisticsDto zawierający statystyki parametru
     */
    public ParameterStatisticsDto getParameter(String period, Integer userId, String parameter, String name)
    {
        LocalDate endDate = LocalDate.now();
        LocalDateTime localDateTimeWithMidnightEnd = endDate.atStartOfDay();
        Timestamp timestampEnd= Timestamp.valueOf(localDateTimeWithMidnightEnd);

        LocalDate startDate = endDate;

        switch(period)
        {
            case "week":
                startDate = endDate.minusDays(7);
                break;
            case "month":
                startDate = endDate.minusDays(30);
                break;
            case "year":
                startDate = endDate.minusDays(365);
                break;
        }
        LocalDateTime localDateTimeWithMidnightStart = startDate.atStartOfDay();
        Timestamp timestampStart= Timestamp.valueOf(localDateTimeWithMidnightStart);

        List<Day> days = dayRepository.findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId);

        if(days != null)
        {
            List<ParameterDto> daysToResponse = new ArrayList<ParameterDto>();
            String parameterName = "";

            for(Day day : days)
            {
                Double parameterValue = 0.0;
                switch (parameter){
                    case "workH":
                        parameterValue = day.getWorkH() != null ? day.getWorkH() : 0.0;
                        parameterName = "Przepracowane godziny";
                        break;
                    case "studyH":
                        parameterValue = day.getStudiesH() != null ? day.getStudiesH() : 0.0;
                        parameterName = "Godziny na uczelni";
                        break;
                    case "learnH":
                        parameterValue = day.getLearningH() != null ? day.getLearningH() : 0.0;
                        parameterName = "Godziny nauki";
                        break;
                    case "sleepH":
                        parameterValue = day.getSleepH() != null ? day.getSleepH() : 0.0;
                        parameterName = "Przespane godziny";
                        break;
                    case "sportH":
                        parameterValue = day.getSportH() != null ? day.getSportH() : 0.0;
                        parameterName = "Godziny spędzone na sporcie";
                        break;
                    case "restH":
                        parameterValue = day.getRestH() != null ? day.getRestH() : 0.0;
                        parameterName = "Godziny odpoczynku";
                        break;
                    case "mealCount":
                        parameterValue = Double.valueOf(day.getMealsNumber()!= null ? day.getMealsNumber() : 0.0);
                        parameterName = "Liczba posiłków";
                        break;
                    case "waterCount":
                        parameterValue = day.getWater() != null ? day.getWater() : 0.0;
                        parameterName = "Ilość wypitej wody";
                        break;
                    case "physicalWellBeeing":
                        parameterValue = day.getPhysicalWellBeeing() != null ? day.getPhysicalWellBeeing() : 0.0;
                        parameterName = "Samopoczucie fizyczne";
                        break;
                    case "mentalWellBeeing":
                        parameterValue = day.getMentalWellBeeing() != null ? day.getMentalWellBeeing() : 0.0;
                        parameterName = "Samopoczucie psychiczne";
                        break;
                    case "drugs":
                        List<Drug> drugs = drugRepository.findByDayIdAndDrugName(day.getId(), name);
                        parameterValue = 0.0;
                        if(drugs != null)
                        {
                            for (Drug drug : drugs) {
                                parameterValue += drug.getDrugAmount() != null ? drug.getDrugAmount() : 0.0;
                            }
                        }
                        parameterName = name;
                        break;
                    case "supplements":
                        List<Supplement> supplements = supplementRepository.findByDayIdAndSupplementName(day.getId(), name);
                        parameterValue = 0.0;
                        if(supplements != null)
                        {
                            for (Supplement supplement : supplements) {
                                parameterValue += supplement.getSupplementAmount() != null ? supplement.getSupplementAmount() : 0.0;
                            }
                        }
                        parameterName = name;
                        break;
                    case "meetingH":
                        Meeting meeting = meetingRepository.findByDayId(day.getId());
                        parameterValue = meeting != null ? meeting.getMeetingH() : 0.0;
                        parameterName = "Ilość czasu na spotkaniach towarzyskich";
                        break;
                    case "peopleCount":
                        Meeting meetingPeople = meetingRepository.findByDayId(day.getId());
                        parameterValue = meetingPeople != null ? meetingPeople.getPeopleCount().doubleValue() : 0.0;
                        parameterName = "Ilość spotkanych osób";
                        break;

                }
                if (parameterValue != 0.0)
                {
                    daysToResponse.add(new ParameterDto(new SimpleDateFormat("yyyy-MM-dd").format(day.getDate()), parameterValue));
                }
            }
            return new ParameterStatisticsDto(daysToResponse, parameterName);
        }
        return new ParameterStatisticsDto();
    }

    /**
     * Pobiera ogólne statystyki.
     *
     * @param period      okres, dla którego mają zostać pobrane statystyki ("week", "month", "year")
     * @param userId      identyfikator użytkownika.
     * @param parameter      parametr.
     * @param name      nazwa suplementu lub używki.
     * @return            obiekt typu StatisticsDto zawierający statystyki
     */
    public StatisticsDto getStatistics(String period, Integer userId, String parameter, String name)
    {
        LocalDate endDate = LocalDate.now();
        LocalDateTime localDateTimeWithMidnightEnd = endDate.atStartOfDay();
        Timestamp timestampEnd= Timestamp.valueOf(localDateTimeWithMidnightEnd);

        LocalDate startDate = endDate;

        switch(period)
        {
            case "week":
                startDate = endDate.minusDays(7);
                break;
            case "month":
                startDate = endDate.minusDays(30);
                break;
            case "year":
                startDate = endDate.minusDays(365);
                break;
        }
        LocalDateTime localDateTimeWithMidnightStart = startDate.atStartOfDay();
        Timestamp timestampStart= Timestamp.valueOf(localDateTimeWithMidnightStart);

        List<Day> days = dayRepository.findAllByDateBetweenAndUserId(timestampStart, timestampEnd, userId);

        if(days != null)
        {
            List<Double> daysToResponse = new ArrayList<Double>();
            String parameterName = "";

            for(Day day : days)
            {
                Double parameterValue = 0.0;
                switch (parameter){
                    case "workH":
                        parameterValue = day.getWorkH() != null ? day.getWorkH() : 0.0;
                        parameterName = "Przepracowane godziny";
                        break;
                    case "studyH":
                        parameterValue = day.getStudiesH() != null ? day.getStudiesH() : 0.0;
                        parameterName = "Godziny na uczelni";
                        break;
                    case "learnH":
                        parameterValue = day.getLearningH() != null ? day.getLearningH() : 0.0;
                        parameterName = "Godziny nauki";
                        break;
                    case "sleepH":
                        parameterValue = day.getSleepH() != null ? day.getSleepH() : 0.0;
                        parameterName = "Przespane godziny";
                        break;
                    case "sportH":
                        parameterValue = day.getSportH() != null ? day.getSportH() : 0.0;
                        parameterName = "Godziny spędzone na sporcie";
                        break;
                    case "restH":
                        parameterValue = day.getRestH() != null ? day.getRestH() : 0.0;
                        parameterName = "Godziny odpoczynku";
                        break;
                    case "mealCount":
                        parameterValue = Double.valueOf(day.getMealsNumber()!= null ? day.getMealsNumber() : 0.0);
                        parameterName = "Liczba posiłków";
                        break;
                    case "waterCount":
                        parameterValue = day.getWater() != null ? day.getWater() : 0.0;
                        parameterName = "Ilość wypitej wody";
                        break;
                    case "physicalWellBeeing":
                        parameterValue = day.getPhysicalWellBeeing() != null ? day.getPhysicalWellBeeing() : 0.0;
                        parameterName = "Samopoczucie fizyczne";
                        break;
                    case "mentalWellBeeing":
                        parameterValue = day.getMentalWellBeeing() != null ? day.getMentalWellBeeing() : 0.0;
                        parameterName = "Samopoczucie psychiczne";
                        break;
                    case "drugs":
                        List<Drug> drugs = drugRepository.findByDayIdAndDrugName(day.getId(), name);
                        parameterValue = 0.0;
                        if(!drugs.isEmpty())
                        {
                            for (Drug drug : drugs) {
                                parameterValue += drug.getDrugAmount() != null ? drug.getDrugAmount() : 0.0;
                            }
                        }
                        parameterName = name;
                        break;
                    case "supplements":
                        List<Supplement> supplements = supplementRepository.findByDayIdAndSupplementName(day.getId(), name);
                        parameterValue = 0.0;
                        if(!supplements.isEmpty())
                        {
                            for (Supplement supplement : supplements) {
                                parameterValue += supplement.getSupplementAmount() != null ? supplement.getSupplementAmount() : 0.0;
                            }
                        }
                        parameterName = name;
                        break;
                    case "meetingH":
                        Meeting meeting = meetingRepository.findByDayId(day.getId());
                        parameterValue = meeting != null ? meeting.getMeetingH() : 0.0;
                        parameterName = "Ilość czasu na spotkaniach towarzyskich";
                        break;
                    case "peopleCount":
                        Meeting meetingPeople = meetingRepository.findByDayId(day.getId());
                        parameterValue = meetingPeople != null ? meetingPeople.getPeopleCount().doubleValue() : 0.0;
                        parameterName = "Ilość spotkanych osób";
                        break;

                }
                if (parameterValue != 0.0)
                {
                    daysToResponse.add(parameterValue);
                }
            }
            if(daysToResponse.isEmpty())
            {
                return new StatisticsDto();
            }
            return new StatisticsDto(daysToResponse, parameterName, daysToResponse.stream().mapToDouble(Double::doubleValue).max().getAsDouble(),daysToResponse.stream().mapToDouble(Double::doubleValue).min().getAsDouble(), daysToResponse.stream().mapToDouble(Double::doubleValue).average().getAsDouble());
        }
        return new StatisticsDto();
    }
}
