package zti.projekt_zti.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import zti.projekt_zti.dto.ErrorDto;

import java.io.IOException;

/**
 * Punk startowy uwierzytelniania użytkownika.
 */
@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Metoda obsługująca punkt startowy uwierzytelniania użytkownika.
     *
     * @param request       obiekt HttpServletRequest
     * @param response      obiekt HttpServletResponse
     * @param authException wyjątek autentykacji
     * @throws IOException      jeśli wystąpi błąd wejścia/wyjścia
     * @throws ServletException jeśli wystąpi błąd serwletu
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        OBJECT_MAPPER.writeValue(response.getOutputStream(), new ErrorDto("Unauthorized path"));
    }
}