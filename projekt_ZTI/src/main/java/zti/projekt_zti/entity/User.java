package zti.projekt_zti.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca encję "User".
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_user")
public class User {

    /**
     * Identyfikator użytkownika.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Imię użytkownika.
     */
    @Column(name = "first_name", nullable = false)
    @Size(max = 50)
    private String firstName;

    /**
     * Nazwisko użytkownika.
     */
    @Column(name = "last_name", nullable = false)
    @Size(max = 50)
    private String lastName;

    /**
     * Login użytkownika.
     */
    @Column(nullable = false)
    @Size(max = 50)
    private String login;

    /**
     * Hasło użytkownika.
     */
    @Column(nullable = false)
    private String password;
}
