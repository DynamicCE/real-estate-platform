package com.erkan.user_service.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    protected BaseException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
