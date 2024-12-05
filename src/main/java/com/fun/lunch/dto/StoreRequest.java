package com.fun.lunch.dto;

import com.fun.lunch.entity.Store;
import jakarta.validation.constraints.NotNull;

public record StoreRequest(
        @NotNull
        String name,
        @NotNull
        double latitude,
        @NotNull
        double longitude,
        @NotNull
        String personalKey,
        String description
) {
    public StoreRequest(String name, double latitude, double longitude, String personalKey) {
        this(name, latitude, longitude, personalKey, "");
    }

    public Store toEntity() {
        return new Store(this);
    }
}
