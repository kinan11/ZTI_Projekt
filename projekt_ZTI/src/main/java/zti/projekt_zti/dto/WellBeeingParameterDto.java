package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO reprezentujące parametr dotyczący samopoczucia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellBeeingParameterDto {

    /**
     * Wartość parametru na osi X.
     */
    private Double x;

    /**
     * Wartość parametru na osi Y.
     */
    private Integer y;
}
