package com.fun.lunch.domain.restaurant.service;

import com.fun.lunch.domain.restaurant.dto.RestaurantRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RestaurantService {

    List<RestaurantResponse> findRestaurantsByPersonalKey(String personalKey);

    RestaurantRequest saveRestaurant(RestaurantRequest restaurantRequest);

    long deleteRestaurant(long id, String personalKey);

    Page<RestaurantResponse> findAllRestaurants();
}