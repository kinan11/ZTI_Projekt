package zti.projekt_zti.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zti.projekt_zti.entity.Day;

import java.sql.Timestamp;

/**
 * Interfejs repozytorium dla encji "Day".
 */
@Repository
public interface Day2Repository extends CrudRepository<Day, Long> {

    /**
     * Usuwa rekordy z encji "Day" na podstawie daty i identyfikatora użytkownika.
     *
     * @param date   data
     * @param userId identyfikator użytkownika
     * @return liczba usuniętych rekordów
     */
    long deleteByDateAndUserId(Timestamp date, Integer userId);
}
