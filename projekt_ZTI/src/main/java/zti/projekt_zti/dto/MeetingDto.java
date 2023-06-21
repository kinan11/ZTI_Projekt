package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zti.projekt_zti.entity.Meeting;

/**
 * DTO reprezentujące spotkanie.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingDto {

    /**
     * Konstruktor przyjmujący obiekt meeting.
     * Konwertuje obiekt encji na DTO.
     *
     * @param meeting Obiekt meeting.
     */
    public MeetingDto(Meeting meeting) {
        meetingH = meeting.getMeetingH();
        peopleCount = meeting.getPeopleCount();
    }

    /**
     * Liczba godzin spędzonych na spotkaniach towarzyskich.
     */
    private Double meetingH;
    /**
     * Liczba spotkanych osób.
     */
    private Integer peopleCount;
    /**
     * Identyfikator dnia.
     */
    private Long dayId;
}
