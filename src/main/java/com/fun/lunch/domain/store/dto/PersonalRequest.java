package com.fun.lunch.domain.store.dto;

import jakarta.validation.constraints.NotNull;

public record PersonalRequest(

        @NotNull String personalKey
) {

}
