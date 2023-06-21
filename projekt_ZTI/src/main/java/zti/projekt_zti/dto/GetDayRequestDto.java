package zti.projekt_zti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * DTO reprezentujące żądanie pobrania informacji o dniu.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDayRequestDto {

    /**
     * Data.
     */
    private Date date;

    /**
     * Identyfikator użytkownika.
     */
    private Integer userId;
}
