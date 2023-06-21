package zti.projekt_zti.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca żądanie uwierzytelnienia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    /**
     * Nazwa użytkownika.
     */
    private String userName;

    /**
     * Hasło użytkownika.
     */
    private String password;
}
