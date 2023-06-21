package zti.projekt_zti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca encję "Meeting".
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "meeting")
public class Meeting {

    /**
     * Identyfikator spotkania.
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
     * Czas trwania spotkań.
     */
    @Column(name = "meeting_h")
    private Double meetingH;

    /**
     * Liczba spotkanych osób.
     */
    @Column(name = "people_count")
    private Integer peopleCount;
}
