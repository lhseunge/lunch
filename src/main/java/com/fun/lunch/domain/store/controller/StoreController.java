package com.fun.lunch.domain.store.controller;

import com.fun.lunch.domain.store.dto.StoreRequest;
import com.fun.lunch.domain.store.dto.StoreResponse;
import com.fun.lunch.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("")
    @Operation(summary = "가게 조회")
    public ResponseEntity<?> getStores(String personalKey) {

        return new ResponseEntity<>(storeService.getStores(personalKey), HttpStatus.OK);
    }

    @GetMapping("/random")
    @Operation(summary = "점심메뉴 뽑기")
    public ResponseEntity<?> getRandomStore(String personalKey) {

        List<StoreResponse> stores = storeService.getStores(personalKey);

        return new ResponseEntity<>(storeService.getRandomStore(stores), HttpStatus.OK);
    }

    @PostMapping()
    @Operation(summary = "가게 저장")
    public ResponseEntity<?> saveStore(@RequestBody @Valid StoreRequest storeRequest) {

        return new ResponseEntity<>(storeService.saveStore(storeRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 삭제")
    public ResponseEntity<?> deleteStore(@PathVariable long id, String personalKey) {

        return new ResponseEntity<>(storeService.deleteStore(id, personalKey), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getStoreTest() {
        return new ResponseEntity<>(storeService.pagingStores(), HttpStatus.OK);
    }
}
