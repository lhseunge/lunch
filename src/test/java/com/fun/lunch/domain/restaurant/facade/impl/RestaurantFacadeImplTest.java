package com.fun.lunch.domain.restaurant.facade.impl;

import com.fun.lunch.domain.restaurant.dto.DrawStatistics;
import com.fun.lunch.domain.restaurant.dto.PersonalRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import com.fun.lunch.domain.restaurant.service.DrawService;
import com.fun.lunch.domain.restaurant.service.PersonalService;
import com.fun.lunch.domain.restaurant.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RestaurantFacadeImplTest {

    private RestaurantService restaurantService;
    private DrawService drawService;
    private PersonalService personalService;
    private RestaurantFacadeImpl restaurantFacade;

    @BeforeEach
    void setUp() {
        restaurantService = mock(RestaurantService.class);
        drawService = mock(DrawService.class);
        personalService = mock(PersonalService.class);
        restaurantFacade = new RestaurantFacadeImpl(restaurantService, drawService, personalService);
    }

    @Test
    @DisplayName("개인키로 식당 목록 조회 시 RestaurantService를 호출한다")
    void getRestaurantsByPersonalKey_호출성공() {
        // given
        String personalKey = "user123";
        List<RestaurantResponse> expectedRestaurants = List.of(
            new RestaurantResponse(1L, "맛집", "맛있는 집", 37.123, 127.456)
        );
        when(restaurantService.findRestaurantsByPersonalKey(personalKey)).thenReturn(expectedRestaurants);

        // when
        List<RestaurantResponse> result = restaurantFacade.getRestaurantsByPersonalKey(personalKey);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("맛집");
        verify(restaurantService).findRestaurantsByPersonalKey(personalKey);
    }

    @Test
    @DisplayName("무작위 식당 추첨 시 DrawService를 호출한다")
    void drawRandomRestaurant_호출성공() {
        // given
        String personalKey = "user123";
        RestaurantResponse expectedRestaurant = new RestaurantResponse(1L, "선택된맛집", "설명", 37.123, 127.456);
        when(drawService.drawRandomRestaurant(personalKey)).thenReturn(expectedRestaurant);

        // when
        RestaurantResponse result = restaurantFacade.drawRandomRestaurant(personalKey);

        // then
        assertThat(result.name()).isEqualTo("선택된맛집");
        verify(drawService).drawRandomRestaurant(personalKey);
    }

    @Test
    @DisplayName("식당 등록 시 개인키 자동 등록 후 식당을 저장한다")
    void registerRestaurant_개인키자동등록후식당저장() {
        // given
        RestaurantRequest request = new RestaurantRequest("새맛집", 37.123, 127.456, "user123", "신규 맛집");

        // when
        restaurantFacade.registerRestaurant(request);

        // then
        verify(personalService).registerPersonalKey("user123");
        verify(restaurantService).saveRestaurant(request);
    }

    @Test
    @DisplayName("식당 삭제 시 RestaurantService를 호출한다")
    void removeRestaurant_호출성공() {
        // given
        long restaurantId = 1L;
        String personalKey = "user123";
        when(restaurantService.deleteRestaurant(restaurantId, personalKey)).thenReturn(restaurantId);

        // when
        long result = restaurantFacade.removeRestaurant(restaurantId, personalKey);

        // then
        assertThat(result).isEqualTo(restaurantId);
        verify(restaurantService).deleteRestaurant(restaurantId, personalKey);
    }

    @Test
    @DisplayName("페이징 조회 시 RestaurantService를 호출한다")
    void getAllRestaurantsWithPaging_호출성공() {
        // given
        List<RestaurantResponse> restaurants = List.of(
            new RestaurantResponse(1L, "맛집1", "설명1", 37.123, 127.456),
            new RestaurantResponse(2L, "맛집2", "설명2", 37.124, 127.457)
        );
        Page<RestaurantResponse> expectedPage = new PageImpl<>(restaurants);
        when(restaurantService.findAllRestaurants()).thenReturn(expectedPage);

        // when
        Page<RestaurantResponse> result = restaurantFacade.getAllRestaurantsWithPaging();

        // then
        assertThat(result.getContent()).hasSize(2);
        verify(restaurantService).findAllRestaurants();
    }

    @Test
    @DisplayName("추첨 통계 조회 시 DrawService를 호출한다")
    void getDrawStatistics_호출성공() {
        // given
        String personalKey = "user123";
        List<DrawStatistics> expectedStats = List.of(
            DrawStatistics.of("맛집A", 3),
            DrawStatistics.of("맛집B", 2)
        );
        when(drawService.getDrawStatistics(personalKey)).thenReturn(expectedStats);

        // when
        List<DrawStatistics> result = restaurantFacade.getDrawStatistics(personalKey);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).restaurantName()).isEqualTo("맛집A");
        assertThat(result.get(0).drawCount()).isEqualTo(3);
        verify(drawService).getDrawStatistics(personalKey);
    }

    @Test
    @DisplayName("개인키 등록 시 PersonalService를 호출한다")
    void registerPersonalKey_호출성공() {
        // given
        PersonalRequest request = new PersonalRequest("user123");

        // when
        restaurantFacade.registerPersonalKey(request);

        // then
        verify(personalService).registerPersonalKey("user123");
    }

    @Test
    @DisplayName("개인키 등록 실패 시에도 식당 등록은 계속 진행된다")
    void registerRestaurant_개인키등록실패시에도식당등록진행() {
        // given
        RestaurantRequest request = new RestaurantRequest("새맛집", 37.123, 127.456, "user123", "신규 맛집");
        doThrow(new RuntimeException("개인키 중복")).when(personalService).registerPersonalKey("user123");

        // when
        restaurantFacade.registerRestaurant(request);

        // then
        verify(personalService).registerPersonalKey("user123");
        verify(restaurantService).saveRestaurant(request); // 개인키 등록 실패해도 식당 등록은 진행
    }
}