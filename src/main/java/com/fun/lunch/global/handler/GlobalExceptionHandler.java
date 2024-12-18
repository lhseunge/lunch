package com.fun.lunch.global.handler;

import com.fun.lunch.global.exception.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<?> exception(ResponseException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception exception) {

        log.error(exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception);
    }
}
