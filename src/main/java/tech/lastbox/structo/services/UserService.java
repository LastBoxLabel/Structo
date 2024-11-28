package tech.lastbox.structo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.lastbox.lastshield.security.core.annotations.UserHandler;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.exception.user.AlreadyExistsException;
import tech.lastbox.structo.exception.user.InvalidDataException;
import tech.lastbox.structo.mappers.UserMapper;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.repositories.UserRepository;

import java.util.Optional;

import static tech.lastbox.structo.util.UserDataValidation.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticateUser(String email, String rawPassword) {
        Optional<UserEntity> user = userRepository.findUserByEmail(email);
        return user.filter(userEntity -> passwordEncoder.matches(rawPassword, userEntity.getPassword())).isPresent();
    }

    public UserDto registerUser(String name, String username, String email, String rawPassword) throws AlreadyExistsException, InvalidDataException {
        if (userRepository.existsByEmail(email)) throw new AlreadyExistsException("Já existe um usuário com este e-mail.");
        if (userRepository.existsByUsername(username)) throw new AlreadyExistsException("Já existe um usuário com este usuário.");

        if (!isValidName(name)) throw new InvalidDataException("O nome informado é inválido.");
        if (!isValidEmail(email)) throw new InvalidDataException("O email informado é inválido.");
        if (!isValidUsername(username)) throw new InvalidDataException("O usuário informado é inválido.");
        if (!isValidPassword(rawPassword)) throw new InvalidDataException("A senha informada é inválida (mínimo 8 caracteres).");

        UserEntity user = userRepository.saveAndFlush(new UserEntity(name, username, email, passwordEncoder.encode(rawPassword)));

        return userMapper.toDto(user);
    }
}
