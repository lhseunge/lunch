package com.fun.lunch.repository;

import com.fun.lunch.entity.PersonalKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalKeyRepository extends CrudRepository<PersonalKey, String> {
    Optional<PersonalKey> findByKey(String key);
}
