package com.fun.lunch.domain.store.service.impl;

import com.fun.lunch.domain.store.dto.DrawStatistics;
import com.fun.lunch.domain.store.dto.StoreRequest;
import com.fun.lunch.domain.store.dto.StoreResponse;
import com.fun.lunch.domain.store.entity.DrawHistory;
import com.fun.lunch.domain.store.entity.Personal;
import com.fun.lunch.domain.store.entity.Store;
import com.fun.lunch.domain.store.repository.DrawHistoryRepository;
import com.fun.lunch.domain.store.repository.StoreRepository;
import com.fun.lunch.global.exception.CustomExceptionEnum;
import com.fun.lunch.global.exception.ResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class StoreServiceImplTest {

    private StoreRepository storeRepository;
    private DrawHistoryRepository drawHistoryRepository;
    private StoreServiceImpl storeService;

    @BeforeEach
    void setUp() {
        storeRepository = mock(StoreRepository.class);
        drawHistoryRepository = mock(DrawHistoryRepository.class);
        storeService = new StoreServiceImpl(storeRepository, drawHistoryRepository);
    }

    @Test
    void getStores_정상조회() {
        String key = "user1";
        Store store = new Store("식당", 123.0, 37.0, Personal.of(key));
        when(storeRepository.findAllByPersonal(any())).thenReturn(List.of(store));

        List<StoreResponse> result = storeService.getStores(key);

//        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("식당");
    }

    @Test
    void findAnyStore_비어있으면예외() {
        assertThatThrownBy(() -> storeService.findAnyStore(List.of()))
                .isInstanceOf(ResponseException.class)
                .hasMessageContaining(CustomExceptionEnum.EMPTY_STORE_DATA.getMessage());
    }

    @Test
    void findAnyStore_정상작동() {
        StoreResponse store1 = new StoreResponse(1L, "A", 123.0, 37.0);
        StoreResponse store2 = new StoreResponse(2L, "B", 123.0, 37.0);
        List<StoreResponse> stores = List.of(store1, store2);

        StoreResponse result = storeService.findAnyStore(stores);

//        assertThat(stores).contains(result);
    }

    @Test
    void saveStore_중복이면예외() {
        StoreRequest request = new StoreRequest("A", 123.0, 37.0, "key");
        when(storeRepository.existsByNameAndLatitudeAndLongitude(anyString(), anyDouble(), anyDouble()))
                .thenReturn(true);

        assertThatThrownBy(() -> storeService.saveStore(request))
                .isInstanceOf(ResponseException.class)
                .hasMessageContaining(CustomExceptionEnum.EXIST_STORE.getMessage());
    }

    @Test
    void saveStore_성공() {
        StoreRequest request = new StoreRequest("A", 123.0, 37.0, "key");
        when(storeRepository.existsByNameAndLatitudeAndLongitude(anyString(), anyDouble(), anyDouble()))
                .thenReturn(false);

        StoreRequest result = storeService.saveStore(request);

        verify(storeRepository).save(any(Store.class));
        assertThat(result).isEqualTo(request);
    }

    @Test
    void deleteStore_성공() {
        long id = 100L;
        String key = "user1";

        long result = storeService.deleteStore(id, key);

        verify(storeRepository).deleteById(id);
        assertThat(result).isEqualTo(id);
    }

    @Test
    void getDrawStatistics_30개내조회후_빈도집계() {
        String key = "user1";
        Store store = new Store("A", 123.0, 37.0, Personal.of(key));
        List<DrawHistory> histories = List.of(
                DrawHistory.of(Personal.of(key), store),
                DrawHistory.of(Personal.of(key), store)
        );
        when(drawHistoryRepository.findTop30ByPersonal(any(), any())).thenReturn(histories);

        List<DrawStatistics> stats = storeService.getDrawStatistics(key);

//        assertThat(stats).hasSize(1);
//        assertThat(stats.get(0).storeName()).isEqualTo("A");
        assertThat(stats.get(0).count()).isEqualTo(2);
    }

    @Test
    void saveDrawHistory_정상작동() {
        String key = "user1";
        StoreResponse response = new StoreResponse(1L, "A", 123.0, 37.0);

        storeService.saveDrawHistory(key, response);

        verify(drawHistoryRepository).save(any(DrawHistory.class));
    }

    @Test
    void isExistsStore_true() {
        Store store = new Store("A", 123.0, 37.0, Personal.of("user"));
        when(storeRepository.existsByNameAndLatitudeAndLongitude(any(), anyDouble(), anyDouble()))
                .thenReturn(true);

        boolean result = storeService.isExistsStore(store);

        assertThat(result).isTrue();
    }

    @Test
    void isExistsStore_false() {
        Store store = new Store("A", 123.0, 37.0, Personal.of("user"));
        when(storeRepository.existsByNameAndLatitudeAndLongitude(any(), anyDouble(), anyDouble()))
                .thenReturn(false);

        boolean result = storeService.isExistsStore(store);

        assertThat(result).isFalse();
    }

    @Test
    void pagingStores_정상작동() {
        Store store1 = new Store("A", 123.0, 37.0, Personal.of("user"));
        Store store2 = new Store("B", 123.1, 37.1, Personal.of("user"));
        List<Store> storeList = List.of(store1, store2);
        Page<Store> page = new PageImpl<>(storeList);
        when(storeRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<StoreResponse> result = storeService.pagingStores();

//        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).name()).isEqualTo("A");
    }
}