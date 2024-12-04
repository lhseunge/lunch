package com.fun.lunch.service;

import com.fun.lunch.dto.StoreRequest;
import com.fun.lunch.dto.StoreResponse;
import com.fun.lunch.entity.Store;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface StoreService {

    @Cacheable(value = "personalKey")
    List<StoreResponse> getStores(String personalKey);

    StoreResponse getRandomStores();
    StoreRequest saveStore(StoreRequest storeRequest);
    long deleteStore(long id);

    boolean isExistsStore(Store store);
}
