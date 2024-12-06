package com.fun.lunch.dto;

import jakarta.validation.constraints.NotNull;

public record PersonalRequest(

        @NotNull String personalKey
) {

}
