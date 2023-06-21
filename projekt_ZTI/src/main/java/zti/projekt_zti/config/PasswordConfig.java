package zti.projekt_zti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Konfiguracja hasła w aplikacji.
 */
@Component
public class PasswordConfig {

    /**
     * Bean dostarczający implementację PasswordEncoder, która używa algorytmu BCrypt do hashowania haseł.
     *
     * @return obiekt PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
