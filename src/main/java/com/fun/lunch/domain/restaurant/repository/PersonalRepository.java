package com.fun.lunch.domain.restaurant.repository;

import com.fun.lunch.domain.restaurant.domain.Personal;
import com.fun.lunch.domain.restaurant.domain.vo.PersonalKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("personalRepositoryV2")
public interface PersonalRepository extends CrudRepository<Personal, PersonalKey> {
}