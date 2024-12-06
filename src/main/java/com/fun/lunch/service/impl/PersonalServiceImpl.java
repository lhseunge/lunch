package com.fun.lunch.service.impl;

import com.fun.lunch.entity.Personal;
import com.fun.lunch.repository.PersonalRepository;
import com.fun.lunch.service.PersonalService;
import org.springframework.stereotype.Service;

@Service
public class PersonalServiceImpl implements PersonalService {

    private final PersonalRepository personalRepository;

    public PersonalServiceImpl(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    @Override
    public void insertPersonalKey(String PersonalKey) {

        boolean isExistPersonalKey = personalRepository.findById(PersonalKey).isPresent();

        if (!isExistPersonalKey) {
            personalRepository.save(new Personal(PersonalKey));
        }

    }
}
