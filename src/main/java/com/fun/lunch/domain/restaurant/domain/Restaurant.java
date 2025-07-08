package com.fun.lunch.domain.restaurant.domain;

import com.fun.lunch.domain.restaurant.domain.vo.Location;
import com.fun.lunch.domain.restaurant.domain.vo.RestaurantName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "store", indexes = {@Index(columnList = "personal_key", name = "personal_idx")})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "name"))
    private RestaurantName restaurantName;
    
    private String description;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "longitude"))
    })
    private Location location;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_key")
    private Personal personal;
    
    private Restaurant(RestaurantName restaurantName, String description, Location location, Personal personal) {
        this.restaurantName = restaurantName;
        this.description = description;
        this.location = location;
        this.personal = personal;
    }
    
    public static Restaurant create(String name, String description, double latitude, double longitude, Personal personal) {
        return new Restaurant(
            RestaurantName.of(name),
            description,
            Location.of(latitude, longitude),
            personal
        );
    }
    
    public boolean isSameRestaurant(Restaurant other) {
        return this.restaurantName.isSameName(other.restaurantName) && 
               this.location.isSameLocation(other.location);
    }
    
    public boolean isOwnedBy(Personal personal) {
        return this.personal.isSamePerson(personal);
    }
    
    public String getName() {
        return restaurantName.getName();
    }
    
    public double getLatitude() {
        return location.getLatitude();
    }
    
    public double getLongitude() {
        return location.getLongitude();
    }
    
    public String getPersonalKey() {
        return personal.getKey();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Restaurant that = (Restaurant) obj;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Restaurant(id=%d, name=%s, location=%s)", id, restaurantName, location);
    }
}