package zti.projekt_zti.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zti.projekt_zti.entity.Supplement;

/**
 * Interfejs repozytorium dla encji "Supplement".
 */
@Repository
public interface Supplement2Repository extends CrudRepository<Supplement, Long> {

    /**
     * Usuwa suplementy na podstawie identyfikatora dnia.
     *
     * @param dayId identyfikator dnia
     * @return liczba usuniętych suplementów
     */
    long deleteByDayId(Long dayId);

}
