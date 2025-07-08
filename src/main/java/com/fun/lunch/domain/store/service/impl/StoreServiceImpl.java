package com.fun.lunch.domain.store.service.impl;

import com.fun.lunch.domain.store.dto.DrawStatistics;
import com.fun.lunch.domain.store.dto.StoreRequest;
import com.fun.lunch.domain.store.dto.StoreResponse;
import com.fun.lunch.domain.store.entity.DrawHistory;
import com.fun.lunch.domain.store.entity.Personal;
import com.fun.lunch.domain.store.entity.Store;
import com.fun.lunch.domain.store.repository.DrawHistoryRepository;
import com.fun.lunch.domain.store.repository.StoreRepository;
import com.fun.lunch.domain.store.service.StoreService;
import com.fun.lunch.global.exception.CustomExceptionEnum;
import com.fun.lunch.global.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final DrawHistoryRepository drawHistoryRepository;

    @Override
    @Cacheable(value = "personalKey")
    public List<StoreResponse> getStores(String personalKey) {

        return storeRepository.findAllByPersonal(Personal.of(personalKey)).stream()
                .map(StoreResponse::toDto)
                .toList();
    }

    @Override
    public StoreResponse findAnyStore(List<StoreResponse> stores) {

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

    @Override
    public Page<StoreResponse> pagingStores() {

        Pageable pageable = PageRequest.of(0, 2);

        Page<Store> stores = storeRepository.findAll(pageable);

        return stores.map(StoreResponse::toDto);
    }

    @Override
    public List<DrawStatistics> getDrawStatistics(String personalKey) {

        List<DrawStatistics> drawStatistics = new ArrayList<>();

        List<DrawHistory> drawHistories = drawHistoryRepository.findTop30ByPersonal(Personal.of(personalKey), Sort.by(DESC, "date"));

        drawHistories.stream()
                .map(drawHistory -> drawHistory.getStore().getName()).distinct().forEach(storeName ->
                drawStatistics.add(
                        new DrawStatistics(
                                storeName,
                                (int) drawHistories.stream().filter(drawHistory -> storeName.equals(drawHistory.getStore().getName())).count())
                )
        );

        return drawStatistics;
    }

    @Override
    public void saveDrawHistory(String personalKey, StoreResponse randomStore) {
        drawHistoryRepository.save(DrawHistory.of(Personal.of(personalKey), Store.of(randomStore, Personal.of(personalKey))));
    }
}
