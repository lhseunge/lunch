package com.fun.lunch.domain.restaurant.dto;

import jakarta.validation.constraints.NotNull;

public record PersonalRequest(
        @NotNull
        String personalKey
) {
}