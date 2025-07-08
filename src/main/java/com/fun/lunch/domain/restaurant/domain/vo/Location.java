package com.fun.lunch.domain.restaurant.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
    
    private double latitude;
    private double longitude;
    
    private Location(double latitude, double longitude) {
        validateLatitude(latitude);
        validateLongitude(longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public static Location of(double latitude, double longitude) {
        return new Location(latitude, longitude);
    }
    
    private void validateLatitude(double latitude) {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("위도는 -90.0과 90.0 사이의 값이어야 합니다.");
        }
    }
    
    private void validateLongitude(double longitude) {
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("경도는 -180.0과 180.0 사이의 값이어야 합니다.");
        }
    }
    
    public boolean isSameLocation(Location other) {
        return Objects.equals(this.latitude, other.latitude) && 
               Objects.equals(this.longitude, other.longitude);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location location = (Location) obj;
        return Double.compare(location.latitude, latitude) == 0 &&
               Double.compare(location.longitude, longitude) == 0;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
    
    @Override
    public String toString() {
        return String.format("Location(lat=%.6f, lng=%.6f)", latitude, longitude);
    }
}