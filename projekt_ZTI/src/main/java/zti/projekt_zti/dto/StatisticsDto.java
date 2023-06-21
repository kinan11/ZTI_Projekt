package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO reprezentujące statystyki.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsDto {

    /**
     * Lista wartości statystyk.
     */
    private List<Double> statistics;

    /**
     * Nazwa parametru, dla którego obliczono statystyki.
     */
    private String parameterName;

    /**
     * Maksymalna wartość statystyki.
     */
    private Double maks;

    /**
     * Minimalna wartość statystyki.
     */
    private Double min;

    /**
     * Średnia wartość statystyki.
     */
    private Double avg;
}
