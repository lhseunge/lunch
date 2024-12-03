package com.fun.lunch.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public enum CustomExceptionEnum {

    EXIST_STORE(HttpStatus.BAD_REQUEST, "S001", "이미 저장된 가게입니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
