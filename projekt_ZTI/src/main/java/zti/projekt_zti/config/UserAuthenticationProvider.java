package zti.projekt_zti.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import zti.projekt_zti.dto.UserDto;
import zti.projekt_zti.service.UserService;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

/**
 * Dostawca uwierzytelniania użytkownika.
 */
@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    private String secretKey = "secret-key";

    private final UserService userService;

    /**
     * Metoda inicjalizująca dostawcę uwierzytelniania.
     * Służy do zakodowania tajnego klucza przy użyciu Base64.
     */
    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Tworzy token uwierzytelniania dla podanego loginu.
     *
     * @param login login użytkownika
     * @return wygenerowany token
     */
    public String createToken(String login) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(login)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    /**
     * Waliduje token uwierzytelniania i zwraca obiekt uwierzytelnienia użytkownika.
     *
     * @param token token uwierzytelniania
     * @return obiekt uwierzytelnienia użytkownika
     */
    public UsernamePasswordAuthenticationToken validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        UserDto user = userService.findByLogin(decoded.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
