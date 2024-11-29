package com.fun.lunch.controller;

import com.fun.lunch.dto.StoreRequest;
import com.fun.lunch.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/lunch")
public class LunchController {

    private final StoreService storeService;

    public LunchController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping()
    @Operation(summary = "가게 조회")
    public ResponseEntity<?> getStores() {

        return new ResponseEntity<>(storeService.getStores(), HttpStatus.OK);
    }

    @GetMapping("/random")
    @Operation(summary = "점심메뉴 뽑기")
    public ResponseEntity<?> getRandomStore() {

        return new ResponseEntity<>(storeService.getRandomStores(), HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "가게 저장")
    public ResponseEntity<?> saveStore(@RequestBody @Valid StoreRequest storeRequest) {

        storeService.saveStore(storeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 삭제")
    public ResponseEntity<?> deleteStore(@PathVariable long id) {

        storeService.deleteStore(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
