package zti.projekt_zti.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import zti.projekt_zti.dto.CredentialsDto;
import zti.projekt_zti.dto.SignUpDto;
import zti.projekt_zti.dto.UserDto;
import zti.projekt_zti.entity.User;
import zti.projekt_zti.exception.AppException;
import zti.projekt_zti.mapper.UserMapper;
import zti.projekt_zti.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(true);
    }

    /**
     * Test przypadku sprawdzania logowania użytkownika przy poprawnych danych uwierzytelniających.
     * Powinien zwrócić obiekt UserDto.
     */
    @Test
    public void testLogin_ValidCredentials_ReturnsUserDto() {
        String login = "john.doe";
        String password = "password";

        User user = new User();
        user.setId(1L);
        user.setLogin(login);
        user.setPassword("hashedPassword");

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setLogin(login);
        credentialsDto.setPassword(password.toCharArray());

        when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(true);
        when(userMapper.toUserDto(user)).thenReturn(new UserDto());

        UserDto result = userService.login(credentialsDto);

        Assertions.assertNotNull(result);
        verify(userRepository).findByLogin(login);
        verify(passwordEncoder).matches(ArgumentMatchers.any(), ArgumentMatchers.any());
        verify(userMapper).toUserDto(user);
    }


    /**
     * Test przypadku sprawdzania logowania użytkownika przy niepoprawnych danych uwierzytelniających.
     * Powinien rzucić wyjątek AppException.
     */
    @Test
    public void testLogin_InvalidCredentials_ThrowsAppException() {
        String login = "john.doe";
        String password = "password";

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setLogin(login);
        credentialsDto.setPassword(password.toCharArray());

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        Assertions.assertThrows(AppException.class, () -> userService.login(credentialsDto));

        verify(userRepository).findByLogin(login);
        Mockito.verifyNoInteractions(passwordEncoder);
        Mockito.verifyNoInteractions(userMapper);
    }

    /**
     * Test przypadku sprawdzania logowania użytkownika przy niepoprawnym haśle.
     * Powinien rzucić wyjątek AppException.
     */
    @Test
    public void testLogin_InvalidPassword_ThrowsAppException() {
        String login = "john.doe";
        char[] password = new String("password").toCharArray();

        User userDto = new User();
        userDto.setId(1L);
        userDto.setLogin(login);

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setLogin(login);
        credentialsDto.setPassword(password);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userDto));
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(false);

        assertThrows(AppException.class, () -> userService.login(credentialsDto));
    }

    /**
     * Test przypadku rejestracji nowego użytkownika.
     * Powinien zwrócić obiekt UserDto.
     */
    @Test
    public void testRegister_NewUser_ReturnsUserDto() {
        String login = "john.doe";
        String password = "password";

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setLogin(login);
        signUpDto.setPassword(password.toCharArray());

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        User user = new User();
        user.setId(1L);
        user.setLogin(login);
        user.setPassword("hashedPassword");

        when(userMapper.signUpToUser(signUpDto)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(new UserDto());

        UserDto result = userService.register(signUpDto);

        Assertions.assertNotNull(result);
        verify(userRepository).findByLogin(login);
        verify(userMapper).signUpToUser(signUpDto);
        verify(passwordEncoder).encode(any());
        verify(userRepository).save(user);
        verify(userMapper).toUserDto(user);
    }

    /**
     * Test przypadku rejestracji użytkownika, gdy użytkownik już istnieje.
     * Powinien rzucić wyjątek AppException.
     */
    @Test
    public void testRegister_UserAlreadyExists_ThrowsAppException() {
        String login = "john.doe";
        String password = "password";

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setLogin(login);
        signUpDto.setPassword(password.toCharArray());

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setLogin(login);
        existingUser.setPassword("hashedPassword");

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(existingUser));

        Assertions.assertThrows(AppException.class, () -> userService.register(signUpDto));
    }

    /**
     * Test przypadku wyszukiwania użytkownika po loginie, gdy użytkownik istnieje.
     * Powinien zwrócić obiekt UserDto.
     */
    @Test
    public void testFindByLogin_ExistingUser_ReturnsUserDto() {
        String login = "john.doe";

        User user = new User();
        user.setId(1L);
        user.setLogin(login);
        user.setPassword("hashedPassword");

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(new UserDto());

        UserDto result = userService.findByLogin(login);

        Assertions.assertNotNull(result);
        verify(userRepository).findByLogin(login);
        verify(userMapper).toUserDto(user);
    }

    /**
     * Test przypadku wyszukiwania użytkownika po loginie, gdy użytkownik nie istnieje.
     * Powinien rzucić wyjątek AppException.
     */
    @Test
    public void testFindByLogin_NonexistentUser_ThrowsAppException() {
        String login = "john.doe";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        Assertions.assertThrows(AppException.class, () -> userService.findByLogin(login));

        verify(userRepository).findByLogin(login);
        Mockito.verifyNoInteractions(userMapper);
    }
}
