package zti.projekt_zti.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zti.projekt_zti.service.StatisticsService;

/**
 * Klasa kontrolera obsługującego żądania związane ze statystykami.
 */
@RequiredArgsConstructor
@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * Obsługuje żądanie pobrania statystyk dla dobrostanu (WellBeeing).
     *
     * @param period okres czasu
     * @param userId identyfikator użytkownika
     * @return ResponseEntity zwracający statystyki w formacie WellBeeingParameterStatisticsDto lub wiadomość o błędzie
     */
    @GetMapping("/wellBeeing")
    public ResponseEntity<?> getWellBeeing(@RequestParam(name = "period", required = false) String period,
                                           @RequestParam(name = "userId", required = false) Integer userId) {
        if (period == null || userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wymagane parametry nie zostały podane.");
        }
        return ResponseEntity.ok(statisticsService.getWellBeeing(period, userId));
    }

    /**
     * Obsługuje żądanie pobrania statystyk dla konkretnego parametru dobrostanu (WellBeeingParameter).
     *
     * @param period     okres czasu
     * @param userId     identyfikator użytkownika
     * @param wellBeeing rodzaj samopoczucia
     * @param parameter  parametr od którego uzależniamy samopoczucie
     * @param name       nazwa suplementu lub używki
     * @return ResponseEntity zwracający statystyki w formacie WellBeeingParameterStatisticsDto lub wiadomość o błędzie
     */
    @GetMapping("/wellBeeingParameter")
    public ResponseEntity<?> getWellBeeingParameter(@RequestParam(name = "period", required = false) String period,
                                                    @RequestParam(name = "userId", required = false) Integer userId,
                                                    @RequestParam(name = "wellBeeing", required = false) String wellBeeing,
                                                    @RequestParam(name = "parameter", required = false) String parameter,
                                                    @RequestParam(name = "name", required = false) String name) {
        if (period == null || userId == null || wellBeeing == null || parameter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wymagane parametry nie zostały podane.");
        }
        return ResponseEntity.ok(statisticsService.getWellBeeingParameter(period, userId, wellBeeing, parameter, name));
    }

    /**
     * Obsługuje żądanie pobrania statystyk dla konkretnego parametru.
     *
     * @param period    okres czasu
     * @param userId    identyfikator użytkownika
     * @param parameter parametr
     * @param name      nazwa suplemetu lub używki
     * @return ResponseEntity zwracający statystyki dla konkretnego parametru w formacie ParameterStatisticsDto lub wiadomość o błędzie
     */
    @GetMapping("/parameter")
    public ResponseEntity<?> getWellBeeingParameter(@RequestParam(name = "period", required = false) String period,
                                                    @RequestParam(name = "userId", required = false) Integer userId,
                                                    @RequestParam(name = "parameter", required = false) String parameter,
                                                    @RequestParam(name = "name", required = false) String name) {
        if (period == null || userId == null || parameter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wymagane parametry nie zostały podane.");
        }
        return ResponseEntity.ok(statisticsService.getParameter(period, userId, parameter, name));
    }

    /**
     * Obsługuje żądanie pobrania ogólnych statystyk.
     *
     * @param period    okres czasu
     * @param userId    identyfikator użytkownika
     * @param parameter parametr
     * @param name      nazwa suplementu lub używki
     * @return ResponseEntity zwracający ogólne statystyki w formacie StatisticsDto lub wiadomość o błędzie
     */
    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(@RequestParam(name = "period", required = false) String period,
                                           @RequestParam(name = "userId", required = false) Integer userId,
                                           @RequestParam(name = "parameter", required = false) String parameter,
                                           @RequestParam(name = "name", required = false) String name) {
        if (period == null || userId == null || parameter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wymagane parametry nie zostały podane.");
        }
        return ResponseEntity.ok(statisticsService.getStatistics(period, userId, parameter, name));
    }
}