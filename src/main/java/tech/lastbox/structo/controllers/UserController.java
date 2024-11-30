package tech.lastbox.structo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.lastbox.structo.dtos.ErrorResponse;
import tech.lastbox.structo.dtos.GenerateRequest;
import tech.lastbox.structo.dtos.MessageRequest;
import tech.lastbox.structo.dtos.MessageResponse;
import tech.lastbox.structo.dtos.auth.UserDto;
import tech.lastbox.structo.exception.NotFoundException;
import tech.lastbox.structo.exception.user.AccessForbidden;
import tech.lastbox.structo.mappers.UserMapper;
import tech.lastbox.structo.model.UserEntity;
import tech.lastbox.structo.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @PostMapping("/project/{historyId}")
    public ResponseEntity<?> sendMessage(@PathVariable("historyId") Long historyId, @RequestBody MessageRequest messageRequest) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            userService.createMessage(messageRequest.message(), user, historyId);

            return ResponseEntity.status(201).body(new MessageResponse("Messagem registrada."));
        } catch (AccessForbidden  e) {
            logger.error("goza em mim bolsonaro");
        } catch (NotFoundException e) {
            logger.error("AI LULAAAAAAAAAAAAAA MEU PRESIDENTE");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Chat não encontrado.", HttpStatus.NOT_FOUND));
    }
}
