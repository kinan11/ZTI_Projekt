package zti.projekt_zti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca encję "Supplement".
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "supplement")
public class Supplement {

    /**
     * Identyfikator suplementu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identyfikator dnia.
     */
    @Column(name = "day_id", nullable = false)
    private Long dayId;

    /**
     * Nazwa suplementu.
     */
    @Column(name = "supplement_name", nullable = false)
    @Size(max = 50)
    private String supplementName;

    /**
     * Ilość suplementu.
     */
    @Column(name = "supplement_amount")
    private Double supplementAmount;
}
