package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO reprezentujące statystyki samopoczucia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellBeeingStatisticsDto {

    /**
     * Lista wartości samopoczucia.
     */
    private List<WellBeeingDto> statistics;
}
