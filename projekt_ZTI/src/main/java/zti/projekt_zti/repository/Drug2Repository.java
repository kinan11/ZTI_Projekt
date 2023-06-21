package zti.projekt_zti.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zti.projekt_zti.entity.Drug;

/**
 * Interfejs repozytorium dla encji "Drug".
 */
@Repository
public interface Drug2Repository extends CrudRepository<Drug, Long> {

    /**
     * Usuwa rekordy z encji "Drug" na podstawie identyfikatora dnia.
     *
     * @param dayId identyfikator dnia
     * @return liczba usuniętych rekordów
     */
    long deleteByDayId(Long dayId);
}
