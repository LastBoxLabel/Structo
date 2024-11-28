package tech.lastbox.structo.dtos.auth;

import tech.lastbox.structo.dtos.UserDto;

public record RegisterResponse(UserDto user, String token) {
}
