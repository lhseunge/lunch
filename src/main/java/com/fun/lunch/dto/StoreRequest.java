package com.fun.lunch.dto;

public record StoreRequest(
        String name,
        String description,
        double latitude,
        double longitude
) {

}
