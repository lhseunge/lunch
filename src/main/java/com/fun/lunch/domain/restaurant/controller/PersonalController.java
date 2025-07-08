package com.fun.lunch.domain.restaurant.controller;

import com.fun.lunch.domain.restaurant.dto.PersonalRequest;
import com.fun.lunch.domain.restaurant.facade.RestaurantFacade;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("personalControllerV2")
@RequestMapping("/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final RestaurantFacade restaurantFacade;

    @PostMapping
    @Operation(summary = "개인 키 등록")
    public ResponseEntity<PersonalRequest> registerPersonalKey(@RequestBody @Valid PersonalRequest personalRequest) {
        restaurantFacade.registerPersonalKey(personalRequest);
        return ResponseEntity.ok(personalRequest);
    }
}