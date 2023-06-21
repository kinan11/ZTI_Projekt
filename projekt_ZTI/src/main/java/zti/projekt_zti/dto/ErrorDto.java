package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO reprezentujące informacje o błędzie.
 */
@AllArgsConstructor
@Data
@Builder
public class ErrorDto {

    /**
     * Wiadomość błędu.
     */
    private String message;
}
