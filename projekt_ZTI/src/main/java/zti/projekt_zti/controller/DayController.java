package zti.projekt_zti.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zti.projekt_zti.dto.GetDayRequestDto;
import zti.projekt_zti.dto.UpdateDayDto;
import zti.projekt_zti.service.DayService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Klasa kontrolera obsługującego żądania związane z dniem (Day).
 */
@RequiredArgsConstructor
@RestController
public class DayController {

    @Autowired
    private DayService dayService;

    /**
     * Obsługuje żądanie pobrania danych dla danego dnia.
     *
     * @param date   data w formacie "yyyy-MM-dd"
     * @param userId identyfikator użytkownika
     * @return ResponseEntity zwracający dane dla danego dnia w formacie GetDayRequestDto lub wiadomość o błędzie
     * @throws ParseException wyjątek rzucany w przypadku problemów z parsowaniem daty
     */
    @GetMapping("/day")
    public ResponseEntity<?> getDay(@RequestParam(name = "date", required = false) String date,
                                    @RequestParam(name = "userId", required = false) Integer userId) throws ParseException {
        if (date == null || userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wymagane parametry nie zostały podane.");
        }
        return ResponseEntity.ok(dayService.getDay(new GetDayRequestDto(new SimpleDateFormat("yyyy-MM-dd").parse(date), userId)));
    }

    /**
     * Obsługuje żądanie aktualizacji danych dla danego dnia.
     *
     * @param dayRequest obiekt DTO zawierający dane do zaktualizowania
     * @return ResponseEntity zwracający wiadomość potwierdzającą zaktualizowanie danych lub wiadomość o błędzie
     */
    @PostMapping("/updateDay")
    public ResponseEntity<String> updateDay(@Valid @RequestBody UpdateDayDto dayRequest) {
        if (dayRequest.getUserId() == null || dayRequest.getDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wymagane parametry nie zostały podane.");
        }
        dayService.updateDay(dayRequest);
        return ResponseEntity.ok("Zaktualizowano dane");
    }

    /**
     * Obsługuje żądanie pobrania wszystkich dni przypisanych do danego użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return ResponseEntity zwracający dane dla wszystkich dni użytkownika lub wiadomość o błędzie
     */
    @GetMapping("/days")
    public ResponseEntity<?> getAllDays(@RequestParam(name = "userId", required = false) Integer userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wymagane parametry nie zostały podane.");
        }
        return ResponseEntity.ok(dayService.getAllDays(userId));
    }

    /**
     * Obsługuje żądanie usunięcia danego dnia.
     *
     * @param date   data w formacie "yyyy-MM-dd"
     * @param userId identyfikator użytkownika
     * @return ResponseEntity zwracający wiadomość potwierdzającą usunięcie dnia lub wiadomość o błędzie
     * @throws ParseException wyjątek rzucany w przypadku problemów z parsowaniem daty
     */
    @DeleteMapping("/deleteDay")
    public ResponseEntity<String> deleteDay(@RequestParam(name = "date", required = false) String date,
                                            @RequestParam(name = "userId", required = false) Integer userId) throws ParseException {
        if (date == null || userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wymagane parametry nie zostały podane.");
        }
        dayService.deleteDay(new GetDayRequestDto(new SimpleDateFormat("yyyy-MM-dd").parse(date), userId));
        return ResponseEntity.ok("Usunieto dzien");
    }
}
