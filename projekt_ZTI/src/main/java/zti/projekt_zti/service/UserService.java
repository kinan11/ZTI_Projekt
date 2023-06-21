package zti.projekt_zti.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zti.projekt_zti.dto.CredentialsDto;
import zti.projekt_zti.dto.SignUpDto;
import zti.projekt_zti.dto.UserDto;
import zti.projekt_zti.exception.AppException;
import zti.projekt_zti.mapper.UserMapper;
import zti.projekt_zti.repository.UserRepository;
import zti.projekt_zti.entity.User;


import java.nio.CharBuffer;
import java.util.Optional;

/**
 * Serwis obsługujący operacje związane z użytkownikami.
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired

    private final UserMapper userMapper;

    /**
     * Metoda logowania użytkownika na podstawie podanych danych uwierzytelniających.
     *
     * @param credentialsDto obiekt zawierający dane uwierzytelniające
     * @return obiekt UserDto reprezentujący zalogowanego użytkownika
     * @throws AppException gdy podane dane uwierzytelniające są nieprawidłowe
     */
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Nieprawidłowy login lub hasło", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Nieprawidłowy login lub hasło", HttpStatus.BAD_REQUEST);
    }

    /**
     * Metoda rejestracji nowego użytkownika.
     *
     * @param userDto obiekt zawierający dane nowego użytkownika
     * @return obiekt UserDto reprezentujący zarejestrowanego użytkownika
     * @throws AppException gdy podany login już istnieje
     */
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Użytkownik o podanym loginie już istnieje", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    /**
     * Metoda wyszukująca użytkownika po podanym loginie.
     *
     * @param login login użytkownika
     * @return obiekt UserDto reprezentujący znalezionego użytkownika
     * @throws AppException gdy użytkownik o podanym loginie nie istnieje
     */
    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Nieprawidłowy login lub hasło", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

}