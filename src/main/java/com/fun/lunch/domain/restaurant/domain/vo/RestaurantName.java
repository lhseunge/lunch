package com.fun.lunch.domain.restaurant.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantName {
    
    private String name;
    
    private RestaurantName(String name) {
        validateName(name);
        this.name = name.trim();
    }
    
    public static RestaurantName of(String name) {
        return new RestaurantName(name);
    }
    
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("식당 이름은 필수입니다.");
        }
        if (name.trim().length() > 50) {
            throw new IllegalArgumentException("식당 이름은 50자를 초과할 수 없습니다.");
        }
    }
    
    public boolean isSameName(RestaurantName other) {
        return Objects.equals(this.name, other.name);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RestaurantName that = (RestaurantName) obj;
        return Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Override
    public String toString() {
        return name;
    }
}