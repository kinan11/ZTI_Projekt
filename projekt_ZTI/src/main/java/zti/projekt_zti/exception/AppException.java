package zti.projekt_zti.exception;

import org.springframework.http.HttpStatus;

/**
 * Wyjątek ogólnego zastosowania w aplikacji.
 */
public class AppException extends RuntimeException {

    /**
     * Kod statusu HTTP.
     */
    private final HttpStatus status;

    /**
     * Konstruktor wyjątku.
     *
     * @param message wiadomość wyjątku
     * @param status  kod statusu HTTP
     */
    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Zwraca kod statusu HTTP związany z wyjątkiem.
     *
     * @return kod statusu HTTP
     */
    public HttpStatus getStatus() {
        return status;
    }
}
