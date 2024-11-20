package com.ewallet.app.exceptions;

import com.ewallet.app.utils.ApiResponseError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseError<String>> notFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ApiResponseError.<String>builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseError<String>> badRequestException(BadRequestException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ApiResponseError.<String>builder()
                        .message(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> validationRequestException(MethodArgumentNotValidException exception) {
        List<String> errorList = exception.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseError.builder()
                        .errors(errorList)
                        .message("Invalid request parameter")
                        .build()
                );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponseError<String>> unauthorizedException(UnauthorizedException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ApiResponseError.<String>builder()
                        .message(exception.getMessage())
                        .build()
                );
    }
}
