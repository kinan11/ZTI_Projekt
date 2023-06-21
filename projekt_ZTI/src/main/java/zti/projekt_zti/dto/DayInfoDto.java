package zti.projekt_zti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * DTO reprezentujące skróconą informację o dniu.
 */
@Data
@AllArgsConstructor
@Builder
public class DayInfoDto {

    /**
     * Identyfikator dnia.
     */
    private Long id;

    /**
     * Data dnia.
     */
    private String date;

    /**
     * Poziom fizycznego samopoczucia.
     */
    private Integer physicalWellBeeing;

    /**
     * Poziom psychicznego samopoczucia.
     */
    private Integer mentalWellBeeing;
}
