package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO reprezentujące dane uwierzytelniające.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialsDto {

    /**
     * Login użytkownika.
     */
    private String login;

    /**
     * Hasło użytkownika.
     */
    private char[] password;
}
