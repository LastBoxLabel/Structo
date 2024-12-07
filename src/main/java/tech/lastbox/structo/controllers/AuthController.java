package tech.lastbox.structo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.lastbox.jwt.JwtService;
import tech.lastbox.jwt.Token;
import tech.lastbox.structo.config.enviroment.JwtProperties;
import tech.lastbox.structo.dtos.ErrorResponse;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.dtos.auth.LoginRequest;
import tech.lastbox.structo.dtos.auth.LoginResponse;
import tech.lastbox.structo.dtos.auth.RegisterRequest;
import tech.lastbox.structo.dtos.auth.RegisterResponse;
import tech.lastbox.structo.exception.user.AlreadyExistsException;
import tech.lastbox.structo.exception.user.InvalidDataException;
import tech.lastbox.structo.services.UserService;

@RestController
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public AuthController(UserService userService, JwtService jwtService, JwtProperties jwtProperties) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean authentication = userService.authenticateUser(loginRequest.email(), loginRequest.password());
        if (authentication) {
            Token token = jwtService.generateToken(loginRequest.email(), jwtProperties.getIssuer());
            return ResponseEntity.ok(new LoginResponse(token.token(), loginRequest.email()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Usuário ou senha inválidos.", HttpStatus.UNAUTHORIZED));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            UserDto userDto = userService.registerUser(registerRequest.name(), registerRequest.username(), registerRequest.email(), registerRequest.password());
            Token token = jwtService.generateToken(registerRequest.email(), jwtProperties.getIssuer());
            return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(userDto, token.token()));
        } catch (AlreadyExistsException alreadyExistsException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(alreadyExistsException, HttpStatus.CONFLICT));
        } catch (InvalidDataException invalidDataException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(invalidDataException, HttpStatus.BAD_REQUEST));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
