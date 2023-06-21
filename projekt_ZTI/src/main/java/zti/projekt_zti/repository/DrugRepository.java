package zti.projekt_zti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import zti.projekt_zti.entity.Drug;

import java.util.List;

/**
 * Interfejs repozytorium dla encji "Drug".
 */
public interface DrugRepository extends JpaRepository<Drug, Long> {

    /**
     * Znajduje suplementy na podstawie identyfikatora dnia.
     *
     * @param id identyfikator dnia
     * @return lista suplementów
     */
    List<Drug> findByDayId(Long id);

    /**
     * Znajduje suplementy na podstawie identyfikatora dnia i nazwy suplementu.
     *
     * @param id   identyfikator dnia
     * @param name nazwa suplementu
     * @return lista suplemetów
     */
    List<Drug> findByDayIdAndDrugName(Long id, String name);

}
