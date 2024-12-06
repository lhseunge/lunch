package com.fun.lunch.exception;

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
}
