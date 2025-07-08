package com.fun.lunch.domain.restaurant.facade.impl;

import com.fun.lunch.domain.restaurant.dto.DrawStatistics;
import com.fun.lunch.domain.restaurant.dto.PersonalRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import com.fun.lunch.domain.restaurant.facade.RestaurantFacade;
import com.fun.lunch.domain.restaurant.service.DrawService;
import com.fun.lunch.domain.restaurant.service.PersonalService;
import com.fun.lunch.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Restaurant 도메인의 복잡한 서비스 조합을 단순화하는 Facade 구현체
 * 
 * 이 클래스는 다음과 같은 역할을 합니다:
 * 1. 여러 서비스 간의 복잡한 상호작용을 캡슐화
 * 2. 클라이언트에게 단순하고 일관된 인터페이스 제공
 * 3. 트랜잭션 경계 관리
 * 4. 비즈니스 플로우 조율
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RestaurantFacadeImpl implements RestaurantFacade {
    
    private final RestaurantService restaurantService;
    private final DrawService drawService;
    private final PersonalService personalService;
    
    @Override
    public List<RestaurantResponse> getRestaurantsByPersonalKey(String personalKey) {
        log.debug("개인키로 식당 목록 조회: {}", personalKey);
        return restaurantService.findRestaurantsByPersonalKey(personalKey);
    }
    
    @Override
    @Transactional
    public RestaurantResponse drawRandomRestaurant(String personalKey) {
        log.info("무작위 식당 추첨 시작: {}", personalKey);
        
        // 1. 무작위 식당 추첨 (내부적으로 추첨 이력도 저장됨)
        RestaurantResponse randomRestaurant = drawService.drawRandomRestaurant(personalKey);
        
        log.info("추첨 완료: {} 식당이 선택됨", randomRestaurant.name());
        return randomRestaurant;
    }
    
    @Override
    @Transactional
    public void registerRestaurant(RestaurantRequest restaurantRequest) {
        log.info("새 식당 등록: {}", restaurantRequest.name());
        
        // 1. 개인키가 등록되어 있지 않다면 자동 등록
        ensurePersonalKeyExists(restaurantRequest.personalKey());
        
        // 2. 식당 등록
        restaurantService.saveRestaurant(restaurantRequest);
        
        log.info("식당 등록 완료: {}", restaurantRequest.name());
    }
    
    @Override
    @Transactional
    public long removeRestaurant(long restaurantId, String personalKey) {
        log.info("식당 삭제: ID={}, PersonalKey={}", restaurantId, personalKey);
        
        long deletedId = restaurantService.deleteRestaurant(restaurantId, personalKey);
        
        log.info("식당 삭제 완료: ID={}", deletedId);
        return deletedId;
    }
    
    @Override
    public Page<RestaurantResponse> getAllRestaurantsWithPaging() {
        log.debug("전체 식당 페이징 조회");
        return restaurantService.findAllRestaurants();
    }
    
    @Override
    public List<DrawStatistics> getDrawStatistics(String personalKey) {
        log.debug("추첨 통계 조회: {}", personalKey);
        return drawService.getDrawStatistics(personalKey);
    }
    
    @Override
    @Transactional
    public void registerPersonalKey(PersonalRequest personalRequest) {
        log.info("개인키 등록: {}", personalRequest.personalKey());
        personalService.registerPersonalKey(personalRequest.personalKey());
        log.info("개인키 등록 완료: {}", personalRequest.personalKey());
    }
    
    /**
     * 개인키가 존재하지 않으면 자동으로 등록하는 헬퍼 메서드
     * 식당 등록 시 개인키가 없어서 실패하는 것을 방지합니다.
     */
    private void ensurePersonalKeyExists(String personalKey) {
        try {
            // 개인키 등록 시도 (이미 존재하면 무시됨)
            personalService.registerPersonalKey(personalKey);
        } catch (Exception e) {
            // 이미 존재하는 키라면 무시하고 계속 진행
            log.debug("개인키가 이미 존재하거나 등록 중 오류 발생: {}", personalKey);
        }
    }
}