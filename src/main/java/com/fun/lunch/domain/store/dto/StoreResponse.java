package com.fun.lunch.domain.store.dto;

import com.fun.lunch.domain.store.entity.Store;

public record StoreResponse(
        long id,
        String name,
        String description,
        double latitude,
        double longitude
) {

    public static StoreResponse toDto(Store store) {
        return new StoreResponse(store.getId(), store.getName(), store.getDescription(), store.getLatitude(), store.getLongitude());
    }

    public StoreResponse(Long id, String name, double latitude, double longitude) {
        this(id, name, null, latitude, longitude);
    }
}
