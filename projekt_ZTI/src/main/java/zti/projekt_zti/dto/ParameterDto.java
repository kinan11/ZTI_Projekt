package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO reprezentujące parametr.
 */
@Data
@AllArgsConstructor
@Builder
public class ParameterDto {

    /**
     * Wartość parametru x.
     */
    private String x;

    /**
     * Wartość parametru y.
     */
    private Double y;
}
