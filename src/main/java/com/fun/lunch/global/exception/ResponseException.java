package com.fun.lunch.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ResponseException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;
    private String errorCode;

    public static ResponseException from(CustomExceptionEnum customExceptionEnum) {
        return new ResponseException(
                customExceptionEnum.getHttpStatus(),
                customExceptionEnum.getMessage(),
                customExceptionEnum.getErrorCode()

        );
    }

    public static ResponseException from(CustomExceptionEnum customExceptionEnum, String message) {
        return new ResponseException(
                customExceptionEnum.getHttpStatus(),
                message,
                customExceptionEnum.getErrorCode());
    }
}
