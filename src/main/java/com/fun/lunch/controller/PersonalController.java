package com.fun.lunch.controller;

import com.fun.lunch.dto.PersonalRequest;
import com.fun.lunch.service.PersonalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personal")
public class PersonalController {

    private final PersonalService personalService;

    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    @PostMapping
    public ResponseEntity<?> addPersonalKey(@RequestBody @Valid PersonalRequest personalRequest) {

        personalService.insertPersonalKey(personalRequest.personalKey());

        return new ResponseEntity<>(personalRequest, HttpStatus.OK);
    }
}
