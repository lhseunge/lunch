package com.fun.lunch.domain.store.dto;

import com.fun.lunch.domain.store.entity.Personal;

public record PersonalResponse(
        String key
) {
    public PersonalResponse(Personal personal) {
        this(personal.getKey());
    }
}
