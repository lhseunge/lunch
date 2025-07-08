package com.fun.lunch.domain.restaurant.controller;

import com.fun.lunch.domain.restaurant.dto.DrawStatistics;
import com.fun.lunch.domain.restaurant.dto.RestaurantRequest;
import com.fun.lunch.domain.restaurant.dto.RestaurantResponse;
import com.fun.lunch.domain.restaurant.facade.RestaurantFacade;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantFacade restaurantFacade;

    @GetMapping
    @Operation(summary = "식당 조회")
    public ResponseEntity<List<RestaurantResponse>> getRestaurants(@RequestParam String personalKey) {
        List<RestaurantResponse> restaurants = restaurantFacade.getRestaurantsByPersonalKey(personalKey);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/random")
    @Operation(summary = "점심메뉴 뽑기")
    public ResponseEntity<RestaurantResponse> getRandomRestaurant(@RequestParam String personalKey) {
        RestaurantResponse randomRestaurant = restaurantFacade.drawRandomRestaurant(personalKey);
        return ResponseEntity.ok(randomRestaurant);
    }

    @PostMapping
    @Operation(summary = "식당 저장")
    public ResponseEntity<Void> saveRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        restaurantFacade.registerRestaurant(restaurantRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "식당 삭제")
    public ResponseEntity<Long> deleteRestaurant(@PathVariable long id, @RequestParam String personalKey) {
        long deletedId = restaurantFacade.removeRestaurant(id, personalKey);
        return ResponseEntity.ok(deletedId);
    }

    @GetMapping("/page")
    @Operation(summary = "식당 페이징 조회")
    public ResponseEntity<Page<RestaurantResponse>> getRestaurantsWithPaging() {
        Page<RestaurantResponse> restaurants = restaurantFacade.getAllRestaurantsWithPaging();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/statistics")
    @Operation(summary = "추첨 통계 조회")
    public ResponseEntity<List<DrawStatistics>> getDrawStatistics(@RequestParam String personalKey) {
        List<DrawStatistics> statistics = restaurantFacade.getDrawStatistics(personalKey);
        return ResponseEntity.ok(statistics);
    }
}