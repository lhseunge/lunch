package com.fun.lunch.repository;

import com.fun.lunch.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByNameAndLatitudeAndLongitude(String name, double latitude, double longitude);
}
