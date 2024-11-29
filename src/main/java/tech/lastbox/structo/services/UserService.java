package tech.lastbox.structo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.exception.user.AlreadyExistsException;
import tech.lastbox.structo.exception.user.InvalidDataException;
import tech.lastbox.structo.exception.user.OllamaException;
import tech.lastbox.structo.mappers.UserMapper;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.repositories.UserRepository;
import tech.lastbox.structo.util.prompt.GENERATE;

import java.util.Optional;

import static tech.lastbox.structo.util.UserDataValidation.*;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final OllamaService ollamaService;
    private final ProjectService projectService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, OllamaService ollamaService, ProjectService projectService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.ollamaService = ollamaService;
        this.projectService = projectService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticateUser(String email, String rawPassword) {
        Optional<UserEntity> user = userRepository.findUserByEmail(email);
        return user.filter(userEntity -> passwordEncoder.matches(rawPassword, userEntity.getPassword())).isPresent();
    }

    public ProjectEntity generateProject(String name, String description, UserEntity user) {
       String structure = getStructure(description);

       String tasks = getTasks(description, structure);

       String diagram = getDiagram(description, structure, tasks);

       return projectService.createProject(name, description, tasks, structure, diagram, user);
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

    private String getStructure(String description) {
        return ollamaService.sendPrompt(
                GENERATE.STRUCTURE.data(description))
                .orElseThrow(OllamaException::new);
    }

    private String getTasks(String description, String structure) {
        return ollamaService.sendPrompt(
                GENERATE.TASK.data(
                        String.format("%s%n%nProject structure: %s",description, structure)))
                .orElseThrow(OllamaException::new);
    }

    private String getDiagram(String description, String structure, String tasks) {
        return ollamaService.sendPrompt(
                GENERATE.DIAGRAM.data(
                        String.format("%s%n%nProject structure: %s%n%nProject tasks: %s", description, structure, tasks)))
                .orElseThrow(OllamaException::new);
    }
}
