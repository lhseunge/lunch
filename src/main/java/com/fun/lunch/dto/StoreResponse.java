package com.fun.lunch.dto;

import com.fun.lunch.entity.Store;

public record StoreResponse(
        long id,
        String name,
        String description,
        double latitude,
        double longitude
) {
    public StoreResponse(Store store) {
        this(store.getId(), store.getName(), store.getDescription(), store.getLatitude(), store.getLongitude());
    }
}
