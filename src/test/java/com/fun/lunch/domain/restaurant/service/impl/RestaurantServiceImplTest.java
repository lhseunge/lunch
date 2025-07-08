package com.fun.lunch.domain.restaurant.service.impl;

import com.fun.lunch.domain.restaurant.domain.Personal;
import com.fun.lunch.domain.restaurant.domain.Restaurant;
import com.fun.lunch.domain.restaurant.dto.RestaurantRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import com.fun.lunch.domain.restaurant.repository.RestaurantRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {

    private RestaurantRepository restaurantRepository;
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp() {
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantService = new RestaurantServiceImpl(restaurantRepository);
    }

    @Test
    void findRestaurantsByPersonalKey_정상조회() {
        String key = "user1";
        Restaurant restaurant = Restaurant.create("식당", "맛있는 식당", 123.0, 37.0, Personal.of(key));
        when(restaurantRepository.findAllByPersonal(any())).thenReturn(List.of(restaurant));

        List<RestaurantResponse> result = restaurantService.findRestaurantsByPersonalKey(key);

        assertThat(result.get(0).name()).isEqualTo("식당");
    }

    @Test
    void saveRestaurant_중복이면예외() {
        RestaurantRequest request = new RestaurantRequest("A", 123.0, 37.0, "key");
        when(restaurantRepository.existsByNameAndLocation(anyString(), anyDouble(), anyDouble()))
                .thenReturn(true);

        assertThatThrownBy(() -> restaurantService.saveRestaurant(request))
                .isInstanceOf(ResponseException.class)
                .hasMessageContaining(CustomExceptionEnum.EXIST_STORE.getMessage());
    }

    @Test
    void saveRestaurant_성공() {
        RestaurantRequest request = new RestaurantRequest("A", 123.0, 37.0, "key");
        when(restaurantRepository.existsByNameAndLocation(anyString(), anyDouble(), anyDouble()))
                .thenReturn(false);

        RestaurantRequest result = restaurantService.saveRestaurant(request);

        verify(restaurantRepository).save(any(Restaurant.class));
        assertThat(result).isEqualTo(request);
    }

    @Test
    void deleteRestaurant_성공() {
        long id = 100L;
        String key = "user1";

        long result = restaurantService.deleteRestaurant(id, key);

        verify(restaurantRepository).deleteById(id);
        assertThat(result).isEqualTo(id);
    }

    @Test
    void findAllRestaurants_정상작동() {
        Restaurant restaurant1 = Restaurant.create("A", "desc1", 123.0, 37.0, Personal.of("user"));
        Restaurant restaurant2 = Restaurant.create("B", "desc2", 123.1, 37.1, Personal.of("user"));
        List<Restaurant> restaurantList = List.of(restaurant1, restaurant2);
        Page<Restaurant> page = new PageImpl<>(restaurantList);
        when(restaurantRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<RestaurantResponse> result = restaurantService.findAllRestaurants();

        assertThat(result.getContent().get(0).name()).isEqualTo("A");
    }
}