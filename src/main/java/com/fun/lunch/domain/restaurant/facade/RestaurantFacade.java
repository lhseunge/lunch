package com.fun.lunch.domain.restaurant.facade;

import com.fun.lunch.domain.restaurant.dto.DrawStatistics;
import com.fun.lunch.domain.restaurant.dto.PersonalRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Restaurant 도메인의 복잡한 서비스들을 통합하여 단순한 인터페이스를 제공하는 Facade
 * 클라이언트는 여러 서비스를 직접 다루지 않고 이 Facade를 통해 작업을 수행할 수 있습니다.
 */
public interface RestaurantFacade {
    
    /**
     * 개인키로 등록된 식당 목록을 조회합니다.
     */
    List<RestaurantResponse> getRestaurantsByPersonalKey(String personalKey);
    
    /**
     * 무작위 식당을 추첨하고 추첨 이력을 저장합니다.
     */
    RestaurantResponse drawRandomRestaurant(String personalKey);
    
    /**
     * 새로운 식당을 등록합니다.
     */
    void registerRestaurant(RestaurantRequest restaurantRequest);
    
    /**
     * 식당을 삭제합니다.
     */
    long removeRestaurant(long restaurantId, String personalKey);
    
    /**
     * 모든 식당을 페이징하여 조회합니다.
     */
    Page<RestaurantResponse> getAllRestaurantsWithPaging();
    
    /**
     * 개인키에 대한 추첨 통계를 조회합니다.
     */
    List<DrawStatistics> getDrawStatistics(String personalKey);
    
    /**
     * 개인키를 등록합니다.
     */
    void registerPersonalKey(PersonalRequest personalRequest);
}