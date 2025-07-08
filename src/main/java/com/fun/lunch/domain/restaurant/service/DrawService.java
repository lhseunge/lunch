package com.fun.lunch.domain.restaurant.service;

import com.fun.lunch.domain.restaurant.dto.DrawStatistics;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;

import java.util.List;

public interface DrawService {

    RestaurantResponse drawRandomRestaurant(String personalKey);

    List<DrawStatistics> getDrawStatistics(String personalKey);
}