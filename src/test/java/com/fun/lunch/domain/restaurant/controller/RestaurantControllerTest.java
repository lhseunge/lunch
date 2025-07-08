package com.fun.lunch.domain.restaurant.controller;

import com.fun.lunch.domain.restaurant.dto.DrawStatistics;
import com.fun.lunch.domain.restaurant.dto.RestaurantRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import com.fun.lunch.domain.restaurant.facade.RestaurantFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantFacade restaurantFacade;

    @Test
    @DisplayName("GET /restaurants - 개인키로 식당 목록 조회")
    void getRestaurants_성공() throws Exception {
        // given
        String personalKey = "user123";
        List<RestaurantResponse> restaurants = List.of(
            new RestaurantResponse(1L, "맛집", "맛있는 집", 37.123, 127.456)
        );
        when(restaurantFacade.getRestaurantsByPersonalKey(personalKey)).thenReturn(restaurants);

        // when & then
        mockMvc.perform(get("/restaurants")
                .param("personalKey", personalKey))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("맛집"));

        verify(restaurantFacade).getRestaurantsByPersonalKey(personalKey);
    }

    @Test
    @DisplayName("GET /restaurants/random - 무작위 식당 추첨")
    void getRandomRestaurant_성공() throws Exception {
        // given
        String personalKey = "user123";
        RestaurantResponse randomRestaurant = new RestaurantResponse(1L, "선택된맛집", "설명", 37.123, 127.456);
        when(restaurantFacade.drawRandomRestaurant(personalKey)).thenReturn(randomRestaurant);

        // when & then
        mockMvc.perform(get("/restaurants/random")
                .param("personalKey", personalKey))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("선택된맛집"));

        verify(restaurantFacade).drawRandomRestaurant(personalKey);
    }

    @Test
    @DisplayName("POST /restaurants - 식당 등록")
    void saveRestaurant_성공() throws Exception {
        // given
        String requestBody = """
            {
                "name": "새맛집",
                "latitude": 37.123,
                "longitude": 127.456,
                "personalKey": "user123",
                "description": "신규 맛집"
            }
            """;

        // when & then
        mockMvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

        verify(restaurantFacade).registerRestaurant(any(RestaurantRequest.class));
    }

    @Test
    @DisplayName("DELETE /restaurants/{id} - 식당 삭제")
    void deleteRestaurant_성공() throws Exception {
        // given
        long restaurantId = 1L;
        String personalKey = "user123";
        when(restaurantFacade.removeRestaurant(restaurantId, personalKey)).thenReturn(restaurantId);

        // when & then
        mockMvc.perform(delete("/restaurants/{id}", restaurantId)
                .param("personalKey", personalKey))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(restaurantFacade).removeRestaurant(restaurantId, personalKey);
    }

    @Test
    @DisplayName("GET /restaurants/page - 페이징 조회")
    void getRestaurantsWithPaging_성공() throws Exception {
        // given
        List<RestaurantResponse> restaurants = List.of(
            new RestaurantResponse(1L, "맛집1", "설명1", 37.123, 127.456)
        );
        Page<RestaurantResponse> page = new PageImpl<>(restaurants);
        when(restaurantFacade.getAllRestaurantsWithPaging()).thenReturn(page);

        // when & then
        mockMvc.perform(get("/restaurants/page"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("맛집1"));

        verify(restaurantFacade).getAllRestaurantsWithPaging();
    }

    @Test
    @DisplayName("GET /restaurants/statistics - 추첨 통계 조회")
    void getDrawStatistics_성공() throws Exception {
        // given
        String personalKey = "user123";
        List<DrawStatistics> statistics = List.of(
            DrawStatistics.of("맛집A", 5)
        );
        when(restaurantFacade.getDrawStatistics(personalKey)).thenReturn(statistics);

        // when & then
        mockMvc.perform(get("/restaurants/statistics")
                .param("personalKey", personalKey))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].restaurantName").value("맛집A"))
                .andExpect(jsonPath("$[0].drawCount").value(5));

        verify(restaurantFacade).getDrawStatistics(personalKey);
    }
}