package zti.projekt_zti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import zti.projekt_zti.entity.Meeting;

/**
 * Interfejs repozytorium dla encji "Meeting".
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    /**
     * Znajduje spotkanie na podstawie identyfikatora dnia.
     *
     * @param id identyfikator dnia
     * @return znalezione spotkanie lub null, jeśli nie znaleziono
     */
    Meeting findByDayId(Long id);

}
