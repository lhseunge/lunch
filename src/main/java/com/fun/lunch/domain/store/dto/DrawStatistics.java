package com.fun.lunch.domain.store.dto;

public record DrawStatistics(
        String store,
        int count
) {
    public DrawStatistics(String store, int count) {
        this.store = store;
        this.count = count;
    }
}

