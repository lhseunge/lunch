package com.fun.lunch.domain.store.dto;

import com.fun.lunch.domain.store.entity.Store;

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
