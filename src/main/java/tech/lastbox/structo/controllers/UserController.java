package tech.lastbox.structo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.lastbox.structo.dtos.GenerateRequest;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.mappers.UserMapper;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDto> index(){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/project")
    public ResponseEntity<?> generate(@RequestBody GenerateRequest generateRequest) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.generateProject(generateRequest.name(), generateRequest.description(), user));
    }
}
