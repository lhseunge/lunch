package com.fun.lunch.entity;

import com.fun.lunch.dto.StoreRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "store", indexes = {@Index(columnList = "personal_key", name = "personal_idx")})
@NoArgsConstructor
public class Store {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private String description;

    private double latitude;
    private double longitude;

    @ManyToOne(cascade = CascadeType.PERSIST)
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
