package tech.lastbox.structo.dtos;

import java.time.LocalDateTime;

public class MessageResponse {
    private final String message;
    private final LocalDateTime timestamp;

    public MessageResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
