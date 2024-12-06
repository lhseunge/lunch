package com.fun.lunch.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public enum CustomExceptionEnum {

    EXIST_STORE(HttpStatus.BAD_REQUEST, "E001", "이미 저장된 가게입니다."),

    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
