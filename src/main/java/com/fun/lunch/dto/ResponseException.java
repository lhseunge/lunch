package com.fun.lunch.dto;

import com.fun.lunch.config.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ResponseException extends RuntimeException {

    private HttpStatus httpStatus;
    private ExceptionCode exception;
}
