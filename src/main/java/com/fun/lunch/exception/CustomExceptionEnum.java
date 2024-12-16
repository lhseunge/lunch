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
    EMPTY_STORE_DATA(HttpStatus.BAD_REQUEST, "E002", "저장된 가게가 없습니다."),


    WORKS_EXCEPTION(HttpStatus.BAD_REQUEST, "W001", "works API Exception."),

    ;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
