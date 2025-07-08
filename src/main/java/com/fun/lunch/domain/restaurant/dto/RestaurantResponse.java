package com.fun.lunch.domain.restaurant.dto;

import com.fun.lunch.domain.restaurant.domain.Restaurant;

public record RestaurantResponse(
        long id,
        String name,
        String description,
        double latitude,
        double longitude
) {

    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
            restaurant.getId(), 
            restaurant.getName(), 
            restaurant.getDescription(), 
            restaurant.getLatitude(), 
            restaurant.getLongitude()
        );
    }

    public RestaurantResponse(Long id, String name, double latitude, double longitude) {
        this(id, name, null, latitude, longitude);
    }
}