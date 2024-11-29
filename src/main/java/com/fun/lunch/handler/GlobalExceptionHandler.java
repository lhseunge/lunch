package com.fun.lunch.handler;

import com.fun.lunch.dto.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<?> exception(ResponseException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception);
    }
}
