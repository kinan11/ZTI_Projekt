package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO reprezentujące statystyki parametru dotyczącego samopoczucia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellBeeingParameterStatisticsDto {

    /**
     * Lista wartości parametrów dotyczących samopoczucia.
     */
    private List<WellBeeingParameterDto> statistics;

    /**
     * Nazwa parametru.
     */
    private String parameterName;

    /**
     * Rodzaj samopoczucia.
     */
    private String wellBeeingName;
}
