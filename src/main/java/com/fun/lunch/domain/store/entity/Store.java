package com.fun.lunch.domain.store.entity;

import com.fun.lunch.domain.store.dto.StoreRequest;
import com.fun.lunch.domain.store.dto.StoreResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "store", indexes = {@Index(columnList = "personal_key", name = "personal_idx")})
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String description;

    private double latitude;
    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_key")
    private Personal personal;

    public Store(StoreRequest storeRequest) {
        this.name = storeRequest.name();
        this.description = storeRequest.description();
        this.latitude = storeRequest.latitude();
        this.longitude = storeRequest.longitude();
        this.personal = Personal.of(storeRequest.personalKey());
    }

    public Store(String name, double latitude, double longitude, Personal personal) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.personal = personal;
    }

    public static Store of(StoreResponse storeResponse, Personal personal) {
        return new Store(storeResponse.id(), storeResponse.name(), storeResponse.description(), storeResponse.latitude(), storeResponse.latitude(), personal);
    }
}
