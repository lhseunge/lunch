package com.fun.lunch.domain.restaurant.dto;

import com.fun.lunch.domain.restaurant.domain.Personal;
import com.fun.lunch.domain.restaurant.domain.Restaurant;
import jakarta.validation.constraints.NotNull;

public record RestaurantRequest(
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
    public RestaurantRequest(String name, double latitude, double longitude, String personalKey) {
        this(name, latitude, longitude, personalKey, "");
    }

    public Restaurant toEntity() {
        return Restaurant.create(
            name, 
            description, 
            latitude, 
            longitude, 
            Personal.of(personalKey)
        );
    }
}