package zti.projekt_zti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zti.projekt_zti.dto.SupplementDto;
import zti.projekt_zti.entity.Drug;
import zti.projekt_zti.entity.Supplement;

import java.util.List;

/**
 * Interfejs repozytorium dla encji "Supplement".
 */
@Repository
public interface SupplementRepository extends JpaRepository<Supplement, Long> {

    /**
     * Znajduje suplementy na podstawie identyfikatora dnia.
     *
     * @param id identyfikator dnia
     * @return lista znalezionych suplementów
     */
    List<Supplement> findByDayId(Long id);

    /**
     * Znajduje suplementy na podstawie identyfikatora dnia i nazwy suplementu.
     *
     * @param id   identyfikator dnia
     * @param name nazwa suplementu
     * @return lista znalezionych suplementów
     */
    List<Supplement> findByDayIdAndSupplementName(Long id, String name);
}
