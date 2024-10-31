package com.fun.whattolun.entity;

import com.fun.whattolun.dto.StoreRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "store")
@NoArgsConstructor
public class Store {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    private String description;

    private double latitude;
    private double longitude;


    public Store(StoreRequest storeRequest) {
        this.name = storeRequest.name();
        this.description = storeRequest.description();
        this.latitude = storeRequest.latitude();
        this.longitude = storeRequest.longitude();
    }
}
