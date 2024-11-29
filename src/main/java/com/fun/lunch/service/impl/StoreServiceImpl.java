package com.fun.lunch.service.impl;

import com.fun.lunch.config.ExceptionCode;
import com.fun.lunch.dto.ResponseException;
import com.fun.lunch.dto.StoreRequest;
import com.fun.lunch.dto.StoreResponse;
import com.fun.lunch.entity.Store;
import com.fun.lunch.repository.StoreRepository;
import com.fun.lunch.service.StoreService;
import org.springframework.http.HttpStatus;
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
    public List<StoreResponse> getStores() {

        return storeRepository.findAll().stream().map(StoreResponse::new).toList();
    }

    @Override
    public StoreResponse getRandomStores() {

        List<StoreResponse> stores = storeRepository.findAll().stream().map(StoreResponse::new).toList();

        int randomIndex = new Random().nextInt(stores.size());

        return stores.get(randomIndex);
    }

    @Override
    public void saveStore(StoreRequest storeRequest) {

        Store store = new Store(storeRequest);
        if (isExistsStore(store)) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ExceptionCode.EXIST_STORE);
        }
        storeRepository.save(store);

    }

    @Override
    public void deleteStore(long id) {
        storeRepository.deleteById(id);
    }

    @Override
    public boolean isExistsStore(Store store) {
        return storeRepository.existsByNameAndLatitudeAndLongitude(store.getName(), store.getLatitude(), store.getLongitude());
    }
}
