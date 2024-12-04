package com.fun.lunch.config;

import lombok.Getter;

@Getter
public enum CacheType {

    PERSONAL_KEY("personalKey", 5 * 60, 10000),
    ;

    private final String name;
    private final Integer expireAfterWrite;
    private final Integer maximumSize;

    CacheType(String name, Integer expireAfterWrite, Integer maximumSize) {
        this.name = name;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }
}
