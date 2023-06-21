package zti.projekt_zti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * DTO reprezentujące aktualizację dnia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDayDto {

    /**
     * Identyfikator użytkownika.
     */
    private Integer userId;

    /**
     * Liczba godzin przeznaczonych na pracę.
     */
    private Double workH;

    /**
     * Liczba godzin przeznaczonych na studiowanie.
     */
    private Double studiesH;

    /**
     * Liczba godzin przeznaczonych na naukę.
     */
    private Double learningH;

    /**
     * Liczba godzin przeznaczonych na sen.
     */
    private Double sleepH;

    /**
     * Liczba godzin przeznaczonych na sport.
     */
    private Double sportH;

    /**
     * Liczba godzin przeznaczonych na odpoczynek.
     */
    private Double restH;

    /**
     * Liczba posiłków spożytych w ciągu dnia.
     */
    private Integer mealsNumber;

    /**
     * Ilość wypitej wody.
     */
    private Double water;

    /**
     * Poziom samopoczucia fizycznego.
     */
    private Integer physicalWellBeeing;

    /**
     * Poziom samopoczucia psychicznego.
     */
    private Integer mentalWellBeeing;

    /**
     * Lista używek.
     */
    private List<DrugDto> drugs;

    /**
     * Lista suplementów.
     */
    private List<SupplementDto> supplements;

    /**
     * Spotkania.
     */
    private MeetingDto meeting;

    /**
     * Data.
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date;

    /**
     * Opis dnia.
     */
    private String description;
}