package com.fun.lunch.domain.store.service;

import com.fun.lunch.domain.store.dto.StoreRequest;
import com.fun.lunch.domain.store.dto.StoreResponse;
import com.fun.lunch.domain.store.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoreService {

    List<StoreResponse> getStores(String personalKey);

    StoreResponse findAnyStore(List<StoreResponse> stores);

    StoreRequest saveStore(StoreRequest storeRequest);

    long deleteStore(long id, String personalKey);

    boolean isExistsStore(Store store);

    Page<StoreResponse> pagingStores();

    void saveDrawHistory(String personalKey, StoreResponse randomStore);
}
