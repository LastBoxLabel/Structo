package tech.lastbox.structo.dtos;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public final class ErrorResponse {
    private final String message;
    private final String status;
    private final LocalDateTime timestamp;

    public ErrorResponse(Throwable exception, HttpStatus status) {
        this.message = exception.getMessage();
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
