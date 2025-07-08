package com.fun.lunch.domain.restaurant.service.impl;

import com.fun.lunch.domain.restaurant.domain.Personal;
import com.fun.lunch.domain.restaurant.repository.PersonalRepository;
import com.fun.lunch.domain.restaurant.service.PersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personalServiceV2")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonalServiceImpl implements PersonalService {

    private final PersonalRepository personalRepository;

    @Override
    @Transactional
    public void registerPersonalKey(String personalKey) {
        Personal personal = Personal.of(personalKey);
        personalRepository.save(personal);
    }
}