package tech.lastbox.structo.mappers;

import org.springframework.stereotype.Component;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.model.UserEntity;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getProjects()
        );
    }
}
