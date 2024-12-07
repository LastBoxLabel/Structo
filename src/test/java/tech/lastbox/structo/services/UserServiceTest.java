package tech.lastbox.structo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import tech.lastbox.structo.dtos.PromptRequest;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.exception.user.AlreadyExistsException;
import tech.lastbox.structo.exception.user.InvalidDataException;
import tech.lastbox.structo.mappers.UserMapper;
import tech.lastbox.structo.model.ProjectEntity;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private OllamaService ollamaService;

    @Mock
    private ProjectService projectService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ChatService chatService;

    @InjectMocks
    private UserService userService;

    private UserEntity mockUser;
    private UserDto mockUserDto;

    @BeforeEach
    void setup() {
        restTemplate = restTemplateBuilder.build();
        mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setName("Test User");
        mockUser.setEmail("user@example.com");
        mockUser.setPassword("12345678");
        mockUser.setUsername("testuser");

        mockUserDto = new UserDto(
                mockUser.getId(),
                mockUser.getName(),
                mockUser.getUsername(),
                mockUser.getEmail(),
                Collections.emptyList()
        );
    }

    @Test
    void usuarioComDadosValidos_realizoRegistro_receboSucesso() throws AlreadyExistsException, InvalidDataException {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(any(UserEntity.class))).thenReturn(mockUser);
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(mockUserDto);

        UserDto result = userService.registerUser(mockUser.getName(), mockUser.getUsername(), mockUser.getEmail(), "password123");

        assertNotNull(result);
        assertEquals(mockUser.getId(), result.id());
        assertEquals(mockUser.getName(), result.name());
        assertEquals(mockUser.getEmail(), result.email());

        verify(userRepository, times(1)).saveAndFlush(any(UserEntity.class));
    }

    @Test
    void usuarioJaCadastrado_realizoRegistro_obtenhoFalha() {
        when(userRepository.existsByEmail(mockUser.getEmail())).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            userService.registerUser(mockUser.getName(), mockUser.getUsername(), mockUser.getEmail(), "password123");
        });

        assertEquals("Já existe um usuário com este e-mail.", exception.getMessage());
    }

    @Test
    void projetoValido_envioSolicitacaoDeCriar_receboSucesso() {
        ProjectEntity mockProject = new ProjectEntity();
        mockProject.setId(1L);
        mockProject.setName("Test Project");
        mockProject.setDescription("Test");

        when(projectService.createProject(anyString(), anyString(), anyString(), anyString(), anyString(), any(UserEntity.class)))
                .thenReturn(mockProject);

        when(ollamaService.sendPrompt(anyString())).thenReturn(sendPrompt(mockProject.getDescription()));

        ProjectEntity result = userService.generateProject("Test Project", "Project Description", mockUser);

        assertNotNull(result);
        assertEquals("Test Project", result.getName());

        verify(projectService, times(1)).createProject(anyString(), anyString(), anyString(), anyString(), anyString(), any(UserEntity.class));
    }

    private Optional<String> sendPrompt(String promptDescription) {
        PromptRequest request = new PromptRequest(
                "qwen2.5-coder:7b",
                promptDescription,
                "json",
                false
        );

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://kklyg.wiremockapi.cloud/api/generate",
                request,
                String.class
        );

        ObjectMapper responseMapper = new ObjectMapper();

        try {
            JsonNode jsonResponse = responseMapper.readTree(response.getBody());
            return Optional.of(jsonResponse.get("response").asText().replace("\\\"", "\""));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}