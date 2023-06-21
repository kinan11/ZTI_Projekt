package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO reprezentujące użytkownika.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    /**
     * Identyfikator użytkownika.
     */
    private Long id;

    /**
     * Imię użytkownika.
     */
    private String firstName;

    /**
     * Nazwisko użytkownika.
     */
    private String lastName;

    /**
     * Login użytkownika.
     */
    private String login;

    /**
     * Token użytkownika.
     */
    private String token;
}
