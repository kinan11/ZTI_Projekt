package zti.projekt_zti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.projekt_zti.entity.User;

import java.util.Optional;

/**
 * Interfejs repozytorium dla encji "User".
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Wyszukuje użytkownika na podstawie loginu.
     *
     * @param login login użytkownika
     * @return opcjonalny obiekt zawierający znalezionego użytkownika, jeśli istnieje
     */
    Optional<User> findByLogin(String login);
}
