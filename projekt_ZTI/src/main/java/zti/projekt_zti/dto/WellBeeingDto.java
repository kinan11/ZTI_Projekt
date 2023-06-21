package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO reprezentujące dane dotyczące samopoczucia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellBeeingDto {

    /**
     * Wartość ośi X.
     */
    private Integer x;

    /**
     * Wartość ośi Y.
     */
    private Integer y;
}
