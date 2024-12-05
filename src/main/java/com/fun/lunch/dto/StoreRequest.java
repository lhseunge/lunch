package com.fun.lunch.dto;

import jakarta.validation.constraints.NotNull;

public record StoreRequest(
        @NotNull
        String name,
        String description,
        @NotNull
        double latitude,
        @NotNull
        double longitude
) {
    public StoreRequest(String name, double latitude, double longitude) {
        this(name, "", latitude, longitude);
    }

    public Store toEntity() {
        return new Store(this);
    }
}
