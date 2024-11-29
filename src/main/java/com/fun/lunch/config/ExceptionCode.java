package com.fun.lunch.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum ExceptionCode {

    EXIST_STORE("S001", "exist store");

    private final String code;
    private final String message;
}
