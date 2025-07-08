package com.fun.lunch.domain.restaurant.dto;

public record DrawStatistics(
        String restaurantName,
        int drawCount
) {
    public static DrawStatistics of(String restaurantName, int drawCount) {
        return new DrawStatistics(restaurantName, drawCount);
    }
}