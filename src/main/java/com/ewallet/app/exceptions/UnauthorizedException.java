package com.ewallet.app.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException {
    private final HttpStatus status = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
