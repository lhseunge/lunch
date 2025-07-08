package com.fun.lunch.domain.restaurant.service.impl;

import com.fun.lunch.domain.restaurant.domain.DrawHistory;
import com.fun.lunch.domain.restaurant.domain.Personal;
import com.fun.lunch.domain.restaurant.domain.Restaurant;
import com.fun.lunch.domain.restaurant.dto.DrawStatistics;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import com.fun.lunch.domain.restaurant.repository.DrawHistoryRepository;
import com.fun.lunch.domain.restaurant.repository.RestaurantRepository;
import com.fun.lunch.global.exception.CustomExceptionEnum;
import com.fun.lunch.global.exception.ResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DrawServiceImplTest {

    private RestaurantRepository restaurantRepository;
    private DrawHistoryRepository drawHistoryRepository;
    private DrawServiceImpl drawService;

    @BeforeEach
    void setUp() {
        restaurantRepository = mock(RestaurantRepository.class);
        drawHistoryRepository = mock(DrawHistoryRepository.class);
        drawService = new DrawServiceImpl(restaurantRepository, drawHistoryRepository);
    }

    @Test
    void drawRandomRestaurant_비어있으면예외() {
        String key = "user1";
        when(restaurantRepository.findAllByPersonal(any())).thenReturn(List.of());

        assertThatThrownBy(() -> drawService.drawRandomRestaurant(key))
                .isInstanceOf(ResponseException.class)
                .hasMessageContaining(CustomExceptionEnum.EMPTY_STORE_DATA.getMessage());
    }

    @Test
    void drawRandomRestaurant_정상작동() {
        String key = "user1";
        Personal personal = Personal.of(key);
        Restaurant restaurant = Restaurant.create("A", "desc", 123.0, 37.0, personal);
        when(restaurantRepository.findAllByPersonal(any())).thenReturn(List.of(restaurant));

        RestaurantResponse result = drawService.drawRandomRestaurant(key);

        verify(drawHistoryRepository).save(any(DrawHistory.class));
        assertThat(result.name()).isEqualTo("A");
    }

    @Test
    void getDrawStatistics_30개내조회후_빈도집계() {
        String key = "user1";
        Personal personal = Personal.of(key);
        Restaurant restaurant = Restaurant.create("A", "desc", 123.0, 37.0, personal);
        List<DrawHistory> histories = List.of(
                DrawHistory.create(personal, restaurant),
                DrawHistory.create(personal, restaurant)
        );
        when(drawHistoryRepository.findTop30ByPersonal(any(), any())).thenReturn(histories);

        List<DrawStatistics> stats = drawService.getDrawStatistics(key);

        assertThat(stats.get(0).drawCount()).isEqualTo(2);
        assertThat(stats.get(0).restaurantName()).isEqualTo("A");
    }
}