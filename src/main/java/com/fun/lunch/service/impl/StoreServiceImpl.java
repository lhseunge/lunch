package com.fun.lunch.service.impl;

import com.fun.lunch.config.CustomExceptionEnum;
import com.fun.lunch.dto.ResponseException;
import com.fun.lunch.dto.StoreRequest;
import com.fun.lunch.dto.StoreResponse;
import com.fun.lunch.entity.Store;
import com.fun.lunch.repository.StoreRepository;
import com.fun.lunch.service.StoreService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    @Cacheable(value = "personalKey")
    public List<StoreResponse> getStores(String personalKey) {

        return storeRepository.findAll().stream().map(StoreResponse::new).toList();
    }

    @Override
    public StoreResponse getRandomStores() {

        List<StoreResponse> stores = storeRepository.findAll().stream().map(StoreResponse::new).toList();

        int randomIndex = new Random().nextInt(stores.size());

        return stores.get(randomIndex);
    }

    @Override
    public StoreRequest saveStore(StoreRequest storeRequest) {

        Store store = storeRequest.toEntity();
        if (isExistsStore(store)) {
            throw ResponseException.from(CustomExceptionEnum.EXIST_STORE);
        }
        storeRepository.save(store);

        return storeRequest;

    }

    @Override
    public long deleteStore(long id) {
        storeRepository.deleteById(id);
        return id;
    }

    @Override
    public boolean isExistsStore(Store store) {
        return storeRepository.existsByNameAndLatitudeAndLongitude(store.getName(), store.getLatitude(), store.getLongitude());
    }
}
