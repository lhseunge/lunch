package com.fun.lunch.controller;

import com.fun.lunch.service.PersonalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonalController {

    private PersonalService personalService;

    public ResponseEntity<?> addPersonalKey(String personalKey) {

        personalService.insertPersonalKey(personalKey);

        return ResponseEntity.ok().build();
    }
}
