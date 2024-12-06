package com.fun.lunch.repository;

import com.fun.lunch.entity.Personal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends CrudRepository<Personal, String> {
}
