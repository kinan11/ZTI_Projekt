package zti.projekt_zti.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zti.projekt_zti.aspect.LoggingAspect;
import zti.projekt_zti.config.UserAuthenticationProvider;
import zti.projekt_zti.dto.CredentialsDto;
import zti.projekt_zti.dto.SignUpDto;
import zti.projekt_zti.dto.UserDto;
import zti.projekt_zti.service.UserService;

import java.net.URI;

/**
 * Klasa kontrolera obsługującego żądania uwierzytelniania i rejestracji użytkowników.
 */
@RequiredArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    /**
     * Obsługuje żądanie logowania użytkownika.
     *
     * @param credentialsDto obiekt DTO (Data Transfer Object) przechowujący dane uwierzytelniające użytkownika
     * @return ResponseEntity zwracający obiekt DTO z danymi użytkownika oraz tokenem uwierzytelniającym
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getLogin()));
        return ResponseEntity.ok(userDto);
    }

    /**
     * Obsługuje żądanie rejestracji nowego użytkownika.
     *
     * @param user obiekt DTO zawierający dane użytkownika do zarejestrowania
     * @return ResponseEntity zwracający obiekt DTO z danymi utworzonego użytkownika oraz tokenem uwierzytelniającym
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser.getLogin()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
