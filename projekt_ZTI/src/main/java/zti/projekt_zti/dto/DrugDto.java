package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zti.projekt_zti.entity.Drug;

/**
 * DTO reprezentujące informacje o używce.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrugDto {

    /**
     * Konstruktor przyjmujący obiekt klasy Drug i inicjalizujący pola DTO na podstawie danych z obiektu.
     *
     * @param drug obiekt klasy Drug
     */
    public DrugDto(Drug drug) {
        drugName = drug.getDrugName();
        drugAmount = drug.getDrugAmount();
        id = drug.getId();
    }

    /**
     * Nazwa używki.
     */
    private String drugName;

    /**
     * Ilość używki.
     */
    private double drugAmount;

    /**
     * ID dnia.
     */
    private Long dayId;

    /**
     * ID używki.
     */
    private Long id;
}
