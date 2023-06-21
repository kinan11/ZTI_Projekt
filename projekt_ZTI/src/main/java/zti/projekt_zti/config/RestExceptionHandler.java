package zti.projekt_zti.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import zti.projekt_zti.dto.ErrorDto;
import zti.projekt_zti.exception.AppException;

/**
 * Obsługa wyjątków dla żądań REST.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Obsługuje wyjątki typu AppException.
     *
     * @param ex wyjątek AppException
     * @return odpowiedź z błędem
     */
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorDto.builder().message(ex.getMessage()).build());
    }
}