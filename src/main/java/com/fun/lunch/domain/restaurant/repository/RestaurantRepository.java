package com.fun.lunch.domain.restaurant.repository;

import com.fun.lunch.domain.restaurant.domain.Personal;
import com.fun.lunch.domain.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE r.restaurantName.name = :name AND r.location.latitude = :latitude AND r.location.longitude = :longitude")
    boolean existsByNameAndLocation(@Param("name") String name, @Param("latitude") double latitude, @Param("longitude") double longitude);

    List<Restaurant> findAllByPersonal(Personal personal);
}