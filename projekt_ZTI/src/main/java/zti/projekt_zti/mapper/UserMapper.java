package zti.projekt_zti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import zti.projekt_zti.dto.SignUpDto;
import zti.projekt_zti.dto.UserDto;
import zti.projekt_zti.entity.User;

/**
 * Interfejs mapujący obiekty związane z użytkownikami.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Mapuje obiekt użytkownika na obiekt DTO użytkownika.
     *
     * @param user obiekt użytkownika
     * @return obiekt DTO użytkownika
     */
    UserDto toUserDto(User user);

    /**
     * Mapuje obiekt DTO rejestracji użytkownika na obiekt użytkownika.
     * Ignoruje mapowanie pola "password".
     *
     * @param signUpDto obiekt DTO rejestracji użytkownika
     * @return obiekt użytkownika
     */
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
