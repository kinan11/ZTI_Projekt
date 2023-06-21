package zti.projekt_zti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO reprezentujące listę informacji o dniach.
 */
@Data
@AllArgsConstructor
@Builder
public class DaysDto {

    /**
     * Lista informacji o poszczególnych dniach.
     */
    private List<DayInfoDto> days;
}
