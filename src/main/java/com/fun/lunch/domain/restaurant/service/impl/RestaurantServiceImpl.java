package com.fun.lunch.domain.restaurant.service.impl;

import com.fun.lunch.domain.restaurant.domain.Personal;
import com.fun.lunch.domain.restaurant.domain.Restaurant;
import com.fun.lunch.domain.restaurant.dto.RestaurantRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import com.fun.lunch.domain.restaurant.repository.RestaurantRepository;
import com.fun.lunch.domain.restaurant.service.RestaurantService;
import com.fun.lunch.global.exception.CustomExceptionEnum;
import com.fun.lunch.global.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    @Cacheable(value = "personalKey")
    public List<RestaurantResponse> findRestaurantsByPersonalKey(String personalKey) {
        Personal personal = Personal.of(personalKey);
        return restaurantRepository.findAllByPersonal(personal).stream()
                .map(RestaurantResponse::from)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = "personalKey", key = "#restaurantRequest.personalKey")
    public RestaurantRequest saveRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRequest.toEntity();
        
        if (isRestaurantExists(restaurant)) {
            throw ResponseException.from(CustomExceptionEnum.EXIST_STORE);
        }
        
        restaurantRepository.save(restaurant);
        return restaurantRequest;
    }

    @Override
    @Transactional
    @CacheEvict(value = "personalKey", key = "#personalKey")
    public long deleteRestaurant(long id, String personalKey) {
        restaurantRepository.deleteById(id);
        return id;
    }

    @Override
    public Page<RestaurantResponse> findAllRestaurants() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        return restaurants.map(RestaurantResponse::from);
    }

    private boolean isRestaurantExists(Restaurant restaurant) {
        return restaurantRepository.existsByNameAndLocation(
            restaurant.getName(), 
            restaurant.getLatitude(), 
            restaurant.getLongitude()
        );
    }
}