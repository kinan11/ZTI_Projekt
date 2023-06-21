package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zti.projekt_zti.entity.Supplement;

/**
 * DTO reprezentujące suplement.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplementDto {

    /**
     * Konstruktor DTO na podstawie obiektu klasy Supplement.
     *
     * @param supplement obiekt klasy Supplement
     */
    public SupplementDto(Supplement supplement) {
        supplementName = supplement.getSupplementName();
        supplementAmount = supplement.getSupplementAmount();
        id = supplement.getId();
    }

    /**
     * Nazwa suplementu.
     */
    private String supplementName;

    /**
     * Ilość suplementu.
     */
    private double supplementAmount;

    /**
     * Identyfikator dnia.
     */
    private Long dayId;

    /**
     * Identyfikator suplementu.
     */
    private Long id;
}
