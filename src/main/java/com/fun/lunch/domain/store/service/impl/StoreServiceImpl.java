package com.fun.lunch.domain.store.service.impl;

import com.fun.lunch.domain.store.dto.StoreRequest;
import com.fun.lunch.domain.store.dto.StoreResponse;
import com.fun.lunch.domain.store.entity.Personal;
import com.fun.lunch.domain.store.entity.Store;
import com.fun.lunch.global.exception.CustomExceptionEnum;
import com.fun.lunch.global.exception.ResponseException;
import com.fun.lunch.domain.store.repository.StoreRepository;
import com.fun.lunch.domain.store.service.StoreService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    @Cacheable(value = "personalKey")
    public List<StoreResponse> getStores(String personalKey) {

        return storeRepository.findAllByPersonal(Personal.of(personalKey)).stream()
                .map(StoreResponse::new)
                .toList();
    }

    @Override
    public StoreResponse getRandomStore(List<StoreResponse> stores) {

        if (stores.isEmpty()) {
            throw ResponseException.from(CustomExceptionEnum.EMPTY_STORE_DATA);
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(stores.size());

        return stores.get(randomIndex);
    }

    @Override
    @CacheEvict(value = "personalKey", key = "#storeRequest.personalKey")
    public StoreRequest saveStore(StoreRequest storeRequest) {

        Store store = storeRequest.toEntity();
        if (isExistsStore(store)) {
            throw ResponseException.from(CustomExceptionEnum.EXIST_STORE);
        }
        storeRepository.save(store);

        return storeRequest;

    }

    @Override
    @CacheEvict(value = "personalKey", key = "#personalKey")
    public long deleteStore(long id, String personalKey) {
        storeRepository.deleteById(id);
        return id;
    }

    @Override
    public boolean isExistsStore(Store store) {
        return storeRepository.existsByNameAndLatitudeAndLongitude(store.getName(), store.getLatitude(), store.getLongitude());
    }
}
