package zti.projekt_zti.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtr odpowiedzialny za uwierzytelnianie żądań przy użyciu tokena JWT.
 */
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider userAuthenticationProvider;

    /**
     * Metoda wywoływana podczas przetwarzania żądania.
     * Weryfikuje nagłówek "Authorization" w żądaniu i w przypadku obecności tokenu JWT
     * wywołuje metodę validateToken z UserAuthenticationProvider.
     * Jeśli weryfikacja tokena jest udana, autoryzacja jest ustawiana w SecurityContextHolder.
     *
     * @param httpServletRequest  obiekt HttpServletRequest
     * @param httpServletResponse obiekt HttpServletResponse
     * @param filterChain         łańcuch filtrów
     * @throws ServletException jeśli wystąpi błąd w servletach
     * @throws IOException      jeśli wystąpi błąd we/wy
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2
                    && "Bearer".equals(authElements[0])) {
                try {
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthenticationProvider.validateToken(authElements[1]));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}