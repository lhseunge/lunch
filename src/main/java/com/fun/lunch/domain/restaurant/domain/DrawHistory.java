package com.fun.lunch.domain.restaurant.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "draw_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrawHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_key")
    private Personal personal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Restaurant restaurant;
    
    @CreationTimestamp
    private LocalDateTime drawDateTime;
    
    private DrawHistory(Personal personal, Restaurant restaurant) {
        this.personal = personal;
        this.restaurant = restaurant;
    }
    
    public static DrawHistory create(Personal personal, Restaurant restaurant) {
        validateDrawHistory(personal, restaurant);
        return new DrawHistory(personal, restaurant);
    }
    
    private static void validateDrawHistory(Personal personal, Restaurant restaurant) {
        if (personal == null) {
            throw new IllegalArgumentException("개인 정보는 필수입니다.");
        }
        if (restaurant == null) {
            throw new IllegalArgumentException("식당 정보는 필수입니다.");
        }
        if (!restaurant.isOwnedBy(personal)) {
            throw new IllegalArgumentException("본인이 등록한 식당만 추첨할 수 있습니다.");
        }
    }
    
    public boolean isDrawnBy(Personal personal) {
        return this.personal.isSamePerson(personal);
    }
    
    public boolean isDrawnRestaurant(Restaurant restaurant) {
        return this.restaurant.isSameRestaurant(restaurant);
    }
    
    public String getRestaurantName() {
        return restaurant.getName();
    }
    
    public String getPersonalKey() {
        return personal.getKey();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DrawHistory that = (DrawHistory) obj;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("DrawHistory(id=%d, personal=%s, restaurant=%s, drawDateTime=%s)", 
                           id, personal.getKey(), restaurant.getName(), drawDateTime);
    }
}