package zti.projekt_zti.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO reprezentujące dane rejestracji.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {

    /**
     * Imię użytkownika.
     */
    @NotEmpty
    private String firstName;

    /**
     * Nazwisko użytkownika.
     */
    @NotEmpty
    private String lastName;

    /**
     * Login użytkownika.
     */
    @NotEmpty
    private String login;

    /**
     * Hasło użytkownika.
     */
    @NotEmpty
    private char[] password;
}
