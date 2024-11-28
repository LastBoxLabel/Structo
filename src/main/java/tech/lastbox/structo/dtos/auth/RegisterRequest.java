package tech.lastbox.structo.dtos.auth;

public record RegisterRequest(String name, String username, String email, String password) {
}