package tech.lastbox.structo.dtos.auth;

public record RegisterResponse(UserDto user, String token) {
}
