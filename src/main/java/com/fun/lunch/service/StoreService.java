package com.fun.lunch.service;

import com.fun.lunch.dto.StoreRequest;
import com.fun.lunch.dto.StoreResponse;
import com.fun.lunch.entity.Store;

import java.util.List;

public interface StoreService {

    List<StoreResponse> getStores();
    StoreResponse getRandomStores();
    void saveStore(StoreRequest storeRequest);
    void deleteStore(long id);

    boolean isExistsStore(Store store);
}
