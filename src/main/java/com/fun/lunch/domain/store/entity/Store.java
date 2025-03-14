package com.fun.lunch.domain.store.entity;

import com.fun.lunch.domain.store.dto.StoreRequest;
import jakarta.persistence.*;
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
    private long id;
    private String name;

    private String description;

    private double latitude;
    private double longitude;

    @ManyToOne()
    @JoinColumn(name = "personal_key")
    private Personal personal;

    public Store(StoreRequest storeRequest) {
        this.name = storeRequest.name();
        this.description = storeRequest.description();
        this.latitude = storeRequest.latitude();
        this.longitude = storeRequest.longitude();
        this.personal = Personal.of(storeRequest.personalKey());
    }
}
