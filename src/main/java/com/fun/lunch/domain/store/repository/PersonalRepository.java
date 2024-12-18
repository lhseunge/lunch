package com.fun.lunch.domain.store.repository;

import com.fun.lunch.domain.store.entity.Personal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends CrudRepository<Personal, String> {
}
