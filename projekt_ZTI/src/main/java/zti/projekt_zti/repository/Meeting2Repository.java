package zti.projekt_zti.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zti.projekt_zti.entity.Meeting;

/**
 * Interfejs repozytorium dla encji "Meeting".
 */
@Repository
public interface Meeting2Repository extends CrudRepository<Meeting, Long> {

    /**
     * Usuwa spotkanie na podstawie identyfikatora dnia.
     *
     * @param dayId identyfikator dnia
     * @return liczba usuniętych spotkań
     */
    long deleteByDayId(Long dayId);

}