/**
 * Klasa zawierająca testy jednostkowe dla kontrolera AuthController.
 */
package zti.projekt_zti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import zti.projekt_zti.config.UserAuthenticationProvider;
import zti.projekt_zti.dto.CredentialsDto;
import zti.projekt_zti.dto.SignUpDto;
import zti.projekt_zti.dto.UserDto;
import zti.projekt_zti.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {

    /**
     * Kontroler, który jest testowany.
     */
    @InjectMocks
    private AuthController authController;
    /**
     * Serwis użytkownika (mock).
     */
    @Mock
    private UserService userService;
    /**
     * Dostawca uwierzytelniania użytkownika (mock).
     */
    @Mock
    private UserAuthenticationProvider userAuthenticationProvider;
    /**
     * MockMvc używane do wykonywania żądań HTTP.
     */
    private MockMvc mockMvc;
    /**
     * Konfiguracja przed wykonaniem testu.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    /**
     * Testuje rejestrację użytkownika.
     *
     * <p>Opis testu:
     * - Tworzy obiekt SignUpDto z danymi użytkownika.
     * - Tworzy oczekiwany obiekt UserDto z danymi utworzonego użytkownika.
     * - Ustawia odpowiednie zachowanie dla metod userService.register() i userAuthenticationProvider.createToken() przy użyciu Mockito.
     * - Wysyła żądanie POST do ścieżki '/register' z danymi rejestracyjnymi w formacie JSON.
     * - Sprawdza, czy odpowiedź ma status 201 Created.
     * - Sprawdza, czy nagłówek 'Location' zawiera prawidłową ścieżkę.
     * - Sprawdza, czy odpowiedź JSON zawiera oczekiwane dane użytkownika.
     * - Sprawdza, czy metoda userService.register() została wywołana dokładnie raz z oczekiwanymi danymi.
     * - Sprawdza, czy metoda userAuthenticationProvider.createToken() została wywołana dokładnie raz z oczekiwanym loginem.
     * @throws Exception jeśli wystąpi błąd podczas wykonywania żądania HTTP
     */
    @Test
    public void testRegister() throws Exception {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setLogin("username1");
        signUpDto.setPassword("password".toCharArray());
        signUpDto.setFirstName("John");
        signUpDto.setLastName("Smith");

        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(1L);
        createdUserDto.setLogin("username1");
        createdUserDto.setFirstName("John");
        createdUserDto.setLastName("Smith");
        createdUserDto.setToken("token");

        Mockito.when(userService.register(Mockito.any(SignUpDto.class))).thenReturn(createdUserDto);
        Mockito.when(userAuthenticationProvider.createToken(Mockito.anyString())).thenReturn("token");

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/users/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("username1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));

        Mockito.verify(userService, Mockito.times(1)).register(Mockito.eq(signUpDto));
        Mockito.verify(userAuthenticationProvider, Mockito.times(1)).createToken(Mockito.eq("username1"));
    }

    /**
     * Testuje logowanie użytkownika.
     *
     * <p>Opis testu:
     * - Tworzy obiekt SignUpDto z danymi użytkownika.
     * - Tworzy oczekiwany obiekt UserDto z danymi utworzonego użytkownika.
     * - Ustawia odpowiednie zachowanie dla metod userService.register() i userAuthenticationProvider.createToken() przy użyciu Mockito.
     * - Wysyła żądanie POST do ścieżki '/register' z danymi rejestracyjnymi w formacie JSON.
     * - Sprawdza, czy odpowiedź ma status 201 Created.
     * - Sprawdza, czy nagłówek 'Location' zawiera prawidłową ścieżkę.
     * - Sprawdza, czy odpowiedź JSON zawiera oczekiwane dane użytkownika.
     * - Sprawdza, czy metoda userService.register() została wywołana dokładnie raz z oczekiwanymi danymi.
     * - Sprawdza, czy metoda userAuthenticationProvider.createToken() została wywołana dokładnie raz z oczekiwanym loginem.
     * @throws Exception jeśli wystąpi błąd podczas wykonywania żądania HTTP
     */
    @Test
    public void testLogin() throws Exception {
        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setLogin("username");
        credentialsDto.setPassword("password".toCharArray());

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setLogin("username");
        userDto.setFirstName("John");
        userDto.setLastName("Smith");
        userDto.setToken("token");

        Mockito.when(userService.login(Mockito.any(CredentialsDto.class))).thenReturn(userDto);
        Mockito.when(userAuthenticationProvider.createToken(Mockito.anyString())).thenReturn("token");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentialsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("username"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));

        Mockito.verify(userService, Mockito.times(1)).login(Mockito.eq(credentialsDto));
        Mockito.verify(userAuthenticationProvider, Mockito.times(1)).createToken(Mockito.eq("username"));
    }

}

