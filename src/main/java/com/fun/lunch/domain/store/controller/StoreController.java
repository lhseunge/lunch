package com.fun.lunch.domain.store.controller;

import com.fun.lunch.domain.store.dto.DrawStatistics;
import com.fun.lunch.domain.store.dto.StoreRequest;
import com.fun.lunch.domain.store.dto.StoreResponse;
import com.fun.lunch.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<List<StoreResponse>> getStores(String personalKey) {

        return ResponseEntity.ok(storeService.getStores(personalKey));
    }

    @GetMapping("/random")
    @Operation(summary = "점심메뉴 뽑기")
    public ResponseEntity<StoreResponse> getRandomStore(String personalKey) {

        List<StoreResponse> stores = storeService.getStores(personalKey);
        StoreResponse randomStore = storeService.findAnyStore(stores);

        return ResponseEntity.ok(randomStore);
    }

    @PostMapping()
    @Operation(summary = "가게 저장")
    public ResponseEntity<Void> saveStore(@RequestBody @Valid StoreRequest storeRequest) {

        storeService.saveStore(storeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 삭제")
    public ResponseEntity<?> deleteStore(@PathVariable long id, String personalKey) {

        return ResponseEntity.ok(storeService.deleteStore(id, personalKey));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StoreResponse>> getStoreTest() {
        return ResponseEntity.ok(storeService.pagingStores());
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<DrawStatistics>> getDrawStatistics(@RequestParam String personalKey) {
        return ResponseEntity.ok(storeService.getDrawStatistics(personalKey));
    }
}
