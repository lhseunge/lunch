package com.fun.whattolun.service;

import com.fun.whattolun.dto.StoreRequest;
import com.fun.whattolun.dto.StoreResponse;

import java.util.List;

public interface StoreService {

    List<StoreResponse> getStores();
    StoreResponse getRandomStores();
    void saveStore(StoreRequest storeRequest);
    void deleteStore(long id);
}
