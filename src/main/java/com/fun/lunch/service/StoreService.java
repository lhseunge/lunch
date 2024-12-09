package com.fun.lunch.service;

import com.fun.lunch.dto.StoreRequest;
import com.fun.lunch.dto.StoreResponse;
import com.fun.lunch.entity.Store;

import java.util.List;

public interface StoreService {

    List<StoreResponse> getStores(String personalKey);

    StoreResponse getRandomStore(List<StoreResponse> stores);

    StoreRequest saveStore(StoreRequest storeRequest);
    long deleteStore(long id, String personalKey);

    boolean isExistsStore(Store store);
}
