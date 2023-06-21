package zti.projekt_zti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zti.projekt_zti.entity.Day;

import java.sql.Timestamp;
import java.util.List;

/**
 * Interfejs repozytorium dla encji "Day".
 */
public interface DayRepository extends JpaRepository<Day, Long> {

    /**
     * Znajduje rekord z encji "Day" na podstawie daty i identyfikatora użytkownika.
     *
     * @param date   data
     * @param userId identyfikator użytkownika
     * @return znaleziony rekord lub null, jeśli nie znaleziono pasującego rekordu
     */
    Day findByDateAndUserId(Timestamp date, Integer userId);

    /**
     * Znajduje wszystkie rekordy z encji "Day".
     *
     * @return lista wszystkich rekordów
     */
    List<Day> findAll();

    /**
     * Znajduje wszystkie rekordy z encji "Day" na podstawie identyfikatora użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return lista rekordów pasujących do podanego identyfikatora użytkownika
     */
    List<Day> findAllByUserId(Integer userId);

    /**
     * Znajduje wszystkie rekordy z encji "Day" pomiędzy określonymi datami i na podstawie identyfikatora użytkownika.
     *
     * @param startDate początkowa data
     * @param endDate   końcowa data
     * @param userId    identyfikator użytkownika
     * @return lista rekordów pasujących do podanych kryteriów
     */
    List<Day> findAllByDateBetweenAndUserId(Timestamp startDate, Timestamp endDate, Integer userId);
}
