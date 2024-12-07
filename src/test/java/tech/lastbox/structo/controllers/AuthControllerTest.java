package tech.lastbox.structo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tech.lastbox.jwt.JwtService;
import tech.lastbox.jwt.Token;
import tech.lastbox.structo.config.SecurityTestConfig;
import tech.lastbox.structo.config.enviroment.JwtProperties;
import tech.lastbox.structo.dtos.auth.LoginRequest;
import tech.lastbox.structo.dtos.auth.RegisterRequest;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.exception.user.AlreadyExistsException;
import tech.lastbox.structo.exception.user.InvalidDataException;
import tech.lastbox.structo.services.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
@Import(SecurityTestConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        Mockito.reset(userService, jwtService);
    }


    /*
     * Dado que tenho dados de login válidos
     * Quando envio esses dados para me autenticar
     * Então recebo status 200 (Ok), o email e o token para de autenticação
     */
    @Test
    void loginValido_facoRequisicaoDeLogin_devoReceberOkEmailEToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");

        Token token = new Token(
                "mocked-token",
                "user@example.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "my-issuer",
                List.of("USER"),
                false
        );

        when(userService.authenticateUser("user@example.com", "password")).thenReturn(true);

        when(jwtService.generateToken("user@example.com", "my-issuer")).thenReturn(token);
        when(jwtProperties.getIssuer()).thenReturn("my-issuer");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.token").value("mocked-token"));
    }

    /*
     * Dado que tenho dados de login inválidos
     * Quando envio esses dados para me autenticar
     * Então recebo status 401 (Unauthorized)
     */
    @Test
    void loginInvalido_facoRequisicaoDeLogin_devoReceberBadRequest() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");

        when(userService.authenticateUser("user@example.com", "password"))
                .thenReturn(false);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    /*
     * Dado que envio dados para me registrar inválidos
     * Quando envio esses dados para me registrar
     * Então recebo status 400 (Bad Request)
     */
    @Test
    void dadosInvalidos_facoRequisicaoDeRegistro_devoReceberBadRequest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("TestUser", "", "user@example.com", "12345678");

        when(userService.registerUser("TestUser", "", "user@example.com", "12345678"))
                .thenThrow(new InvalidDataException("Username inválido"));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    /*
     * Dado que envio dados para me registrar válidos
     * Quando envio esses dados para me registrar
     * Então recebo status 200 (Ok), o usuário com seus dados (exceto senha) e um token para se autenticar
     */
    @Test
    void dadosValidos_facoRequisicaoDeRegistro_devoReceberUsuarioEToken() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("TestUser", "username", "user@example.com", "12345678");

        when(userService.registerUser("TestUser", "username", "user@example.com", "12345678"))
                .thenReturn(new UserDto(1L, "TestUser", "username", "user@example.com", Collections.emptyList()));

        Token token = new Token(
                "mocked-token",
                "user@example.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "my-issuer",
                List.of("USER"),
                false
        );

        when(jwtService.generateToken("user@example.com", "my-issuer")).thenReturn(token);
        when(jwtProperties.getIssuer()).thenReturn("my-issuer");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.email").value("user@example.com"))
                .andExpect(jsonPath("$.user.name").value("TestUser"))
                .andExpect(jsonPath("$.user.username").value("username"))
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.token").value("mocked-token"));
    }


    @Test
    void dadosValidos_facoRequisicaoDeRegistroDuplicada_devoReceberConflict() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("TestUser", "username", "user@example.com", "12345678");

        when(userService.registerUser("TestUser", "username", "user@example.com", "12345678"))
                .thenReturn(new UserDto(1L, "TestUser", "username", "user@example.com", Collections.emptyList()));

        Token token = new Token(
                "mocked-token",
                "user@example.com",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "my-issuer",
                List.of("USER"),
                false
        );

        when(jwtService.generateToken("user@example.com", "my-issuer")).thenReturn(token);
        when(jwtProperties.getIssuer()).thenReturn("my-issuer");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated());

        when(userService.registerUser("TestUser", "username", "user@example.com", "12345678"))
                .thenThrow(new AlreadyExistsException("Já existe um usuário com este e-mail."));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isConflict());
    }
}
