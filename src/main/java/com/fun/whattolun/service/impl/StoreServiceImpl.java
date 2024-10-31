package com.fun.whattolun.service.impl;

import com.fun.whattolun.dto.StoreRequest;
import com.fun.whattolun.dto.StoreResponse;
import com.fun.whattolun.entity.Store;
import com.fun.whattolun.repository.StoreRepository;
import com.fun.whattolun.service.StoreService;
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

        storeRepository.save(store);

    }

    @Override
    public void deleteStore(long id) {
        storeRepository.deleteById(id);
    }
}
