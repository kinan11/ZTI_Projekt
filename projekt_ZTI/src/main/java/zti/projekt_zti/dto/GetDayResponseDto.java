package zti.projekt_zti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zti.projekt_zti.dto.DrugDto;
import zti.projekt_zti.dto.MeetingDto;
import zti.projekt_zti.dto.SupplementDto;
import zti.projekt_zti.entity.Day;
import zti.projekt_zti.entity.Drug;
import zti.projekt_zti.entity.Meeting;
import zti.projekt_zti.entity.Supplement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DTO reprezentujące odpowiedź zawierającą informacje o dniu.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetDayResponseDto {

    /**
     * Konstruktor przyjmujący datę i identyfikator użytkownika.
     *
     * @param date   Data w formacie "yyyy-MM-dd".
     * @param userId Identyfikator użytkownika.
     */
    public GetDayResponseDto(String date, int userId) {
        this.date = date;
        this.userId = userId;
    }

    /**
     * Konstruktor przyjmujący obiekt dnia, listę suplementów, spotkanie oraz listę używek.
     * Konwertuje obiekty encji na DTO.
     *
     * @param day            Obiekt dnia.
     * @param supplementsList Lista suplementów.
     * @param meeting        Spotkanie.
     * @param drugsList      Lista używek.
     */
    public GetDayResponseDto(Day day, List<Supplement> supplementsList, Meeting meeting, List<Drug> drugsList) {
        userId = day.getUserId();
        workH = day.getWorkH();
        studiesH = day.getStudiesH();
        learningH = day.getLearningH();
        sleepH = day.getSleepH();
        sportH = day.getSportH();
        restH = day.getRestH();
        mealsNumber = day.getMealsNumber();
        water = day.getWater();
        physicalWellBeeing = day.getPhysicalWellBeeing();
        mentalWellBeeing = day.getMentalWellBeeing();

        List<DrugDto> drugList = new ArrayList<>();
        for (Drug drug : drugsList) {
            drugList.add(new DrugDto(drug));
        }
        drugs = drugList;

        List<SupplementDto> supplementList = new ArrayList<>();
        for (Supplement supplement : supplementsList) {
            supplementList.add(new SupplementDto(supplement));
        }
        supplements = supplementList;

        this.meeting = meeting != null ? new MeetingDto(meeting) : null;

        date = new SimpleDateFormat("yyyy-MM-dd").format(day.getDate());
        description = day.getDescription();
    }

    /**
     * Identyfikator użytkownika.
     */
    private Integer userId;
    /**
     * Liczba przepracowanych godzin.
     */
    private Double workH;
    /**
     * Liczba godzin spędzonych na studiach.
     */
    private Double studiesH;
    /**
     * Liczba godzin spędzonych na nauce.
     */
    private Double learningH;
    /**
     * Liczba przespanych godzin.
     */
    private Double sleepH;
    /**
     * Liczba godzin poświęconych na sport.
     */
    private Double sportH;
    /**
     * Liczba godzin poświęconych na odpoczynek.
     */
    private Double restH;
    /**
     * Liczba spożytych posiłków.
     */
    private Integer mealsNumber;
    /**
     * Ilość wypitej wody.
     */
    private Double water;
    /**
     * Wskaźnik stanu fizycznego.
     */
    private Integer physicalWellBeeing;
    /**
     * Wskaźnik stanu psychicznego.
     */
    private Integer mentalWellBeeing;
    /**
     * Używki.
     */
    private List<DrugDto> drugs;
    /**
     * Suplementy.
     */
    private List<SupplementDto> supplements;
    /**
     * Spotkanie.
     */
    private MeetingDto meeting;
    /**
     * Data.
     */
    private String date;
    /**
     * Opis dnia.
     */
    private String description;
}
