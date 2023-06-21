package zti.projekt_zti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca encję "Drug".
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "drug")
public class Drug {

    /**
     * Identyfikator używki.
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
     * Nazwa użwyki.
     */
    @Column(name = "drug_name", nullable = false)
    @Size(max = 50)
    private String drugName;

    /**
     * Ilość używki.
     */
    @Column(name = "drug_amount")
    private Double drugAmount;
}
