package zti.projekt_zti.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO reprezentujące statystyki parametrów.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParameterStatisticsDto {
    /**
     * Statystyki.
     */
    private List<ParameterDto> statistics;
    /**
     * Nawa parametru.
     */
    private String parameterName;
}