package com.fun.lunch.domain.restaurant.service.impl;

import com.fun.lunch.domain.restaurant.domain.DrawHistory;
import com.fun.lunch.domain.restaurant.domain.Personal;
import com.fun.lunch.domain.restaurant.domain.Restaurant;
import com.fun.lunch.domain.restaurant.dto.DrawStatistics;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import com.fun.lunch.domain.restaurant.repository.DrawHistoryRepository;
import com.fun.lunch.domain.restaurant.repository.RestaurantRepository;
import com.fun.lunch.domain.restaurant.service.DrawService;
import com.fun.lunch.global.exception.CustomExceptionEnum;
import com.fun.lunch.global.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrawServiceImpl implements DrawService {

    private final RestaurantRepository restaurantRepository;
    private final DrawHistoryRepository drawHistoryRepository;

    @Override
    @Transactional
    public RestaurantResponse drawRandomRestaurant(String personalKey) {
        Personal personal = Personal.of(personalKey);
        List<Restaurant> restaurants = restaurantRepository.findAllByPersonal(personal);
        
        if (restaurants.isEmpty()) {
            throw ResponseException.from(CustomExceptionEnum.EMPTY_STORE_DATA);
        }
        
        Restaurant randomRestaurant = selectRandomRestaurant(restaurants);
        saveDrawHistory(personal, randomRestaurant);
        
        return RestaurantResponse.from(randomRestaurant);
    }

    @Override
    public List<DrawStatistics> getDrawStatistics(String personalKey) {
        Personal personal = Personal.of(personalKey);
        List<DrawHistory> drawHistories = drawHistoryRepository.findTop30ByPersonal(personal, Sort.by(DESC, "drawDateTime"));
        
        Map<String, Long> statisticsMap = drawHistories.stream()
                .collect(Collectors.groupingBy(
                    DrawHistory::getRestaurantName,
                    Collectors.counting()
                ));
        
        return statisticsMap.entrySet().stream()
                .map(entry -> DrawStatistics.of(entry.getKey(), entry.getValue().intValue()))
                .toList();
    }

    private Restaurant selectRandomRestaurant(List<Restaurant> restaurants) {
        int randomIndex = ThreadLocalRandom.current().nextInt(restaurants.size());
        return restaurants.get(randomIndex);
    }

    private void saveDrawHistory(Personal personal, Restaurant restaurant) {
        DrawHistory drawHistory = DrawHistory.create(personal, restaurant);
        drawHistoryRepository.save(drawHistory);
    }
}