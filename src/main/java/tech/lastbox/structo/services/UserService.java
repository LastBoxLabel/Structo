package tech.lastbox.structo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.exception.NotFoundException;
import tech.lastbox.structo.exception.user.AccessForbidden;
import tech.lastbox.structo.exception.user.AlreadyExistsException;
import tech.lastbox.structo.exception.user.InvalidDataException;
import tech.lastbox.structo.exception.user.OllamaException;
import tech.lastbox.structo.mappers.UserMapper;
import tech.lastbox.structo.model.ChatHistory;
import tech.lastbox.structo.model.ChatMessage;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.model.types.Sender;
import tech.lastbox.structo.repositories.UserRepository;
import tech.lastbox.structo.util.prompt.Generate;

import java.util.Optional;

import static tech.lastbox.structo.util.UserDataValidation.*;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final OllamaService ollamaService;
    private final ProjectService projectService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ChatService chatService;

    public UserService(UserRepository userRepository, UserMapper userMapper, OllamaService ollamaService, ProjectService projectService, BCryptPasswordEncoder passwordEncoder, ChatService chatService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.ollamaService = ollamaService;
        this.projectService = projectService;
        this.passwordEncoder = passwordEncoder;
        this.chatService = chatService;
    }

    public boolean authenticateUser(String email, String rawPassword) {
        Optional<UserEntity> user = userRepository.findUserByEmail(email);
        return user.filter(userEntity -> passwordEncoder.matches(rawPassword, userEntity.getPassword())).isPresent();
    }

    @Transactional
    public ProjectEntity generateProject(String name, String description, UserEntity user) {
       String structure = getStructure(description);

       String tasks = getTasks(description, structure);

       String diagram = getDiagram(description, structure, tasks);

       ProjectEntity project = projectService.createProject(name, description, tasks, structure, diagram, user);

       userRepository.save(user.addProject(project));

       return project;
    }

    @Transactional
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

    @Transactional
    public ChatMessage createMessage(String message, UserEntity user, Long historyId) throws AccessForbidden, NotFoundException, JsonProcessingException {
        ChatHistory history = chatService.createUserMessage(historyId, message, user);

        String response = ollamaService.sendPrompt(
                Generate.USERMESSAGE.data(
                        message,
                        chatService.getAllMessagesByChatHistoryId(historyId),
                        history.getBaseInfo())
                ).orElseThrow(OllamaException::new);

        ChatMessage responseMessage = chatService.addMessage(Sender.MODEL, treatResponseForUserMessage(response), history);

        chatService.updateHistory(history.addChatMessages(responseMessage));

        return responseMessage;
    }

    private String getStructure(String description) {
        return ollamaService.sendPrompt(
                            Generate.STRUCTURE.data(description))
                            .orElseThrow(OllamaException::new);
    }

    private String getTasks(String description, String structure) {
        return ollamaService.sendPrompt(
                            Generate.TASK.data(
                                    String.format("%s%n%nProject structure: %s",description, structure)))
                            .orElseThrow(OllamaException::new);
    }

    private String getDiagram(String description, String structure, String tasks) {
        return ollamaService.sendPrompt(
                            Generate.DIAGRAM.data(
                                    String.format("%s%n%nProject structure: %s%n%nProject tasks: %s", description, structure, tasks)))
                            .orElseThrow(OllamaException::new);
    }

    private String treatResponseForUserMessage(String rawResponse) throws JsonProcessingException {
        ObjectMapper responseMapper = new ObjectMapper();
        JsonNode jsonResponse = responseMapper.readTree(rawResponse);
        return jsonResponse.get("message").asText();
    }
}
