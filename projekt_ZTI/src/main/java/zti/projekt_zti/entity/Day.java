package zti.projekt_zti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Klasa reprezentująca encję "Day".
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "day")
public class Day {

    /**
     * Identyfikator dnia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    /**
     * Identyfikator użytkownika.
     */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /**
     * Czas pracy.
     */
    @Column(name = "work_h")
    private Double workH;

    /**
     * Czas nauki na studiach.
     */
    @Column(name = "studies_h")
    private Double studiesH;

    /**
     * Czas nauki.
     */
    @Column(name = "learning_h")
    private Double learningH;

    /**
     * Czas snu.
     */
    @Column(name = "sleep_h")
    private Double sleepH;

    /**
     * Czas spędzony na aktywności sportowej.
     */
    @Column(name = "sport_h")
    private Double sportH;

    /**
     * Czas odpoczynku.
     */
    @Column(name = "rest_h")
    private Double restH;

    /**
     * Liczba posiłków.
     */
    @Column(name = "meals_number")
    private Integer mealsNumber;

    /**
     * Ilość wypitej wody.
     */
    private Double water;

    /**
     * Poziom stanu fizycznego.
     */
    @Column(name = "physical_well_beeing", nullable = false)
    private Integer physicalWellBeeing;

    /**
     * Poziom stanu psychicznego.
     */
    @Column(name = "mental_well_beeing", nullable = false)
    private Integer mentalWellBeeing;

    /**
     * Data.
     */
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * Opis dnia.
     */
    private String description;
}
