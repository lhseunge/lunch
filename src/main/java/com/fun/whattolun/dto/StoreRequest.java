package com.fun.whattolun.dto;

public record StoreRequest(
        String name,
        String description,
        double latitude,
        double longitude
) {

}
