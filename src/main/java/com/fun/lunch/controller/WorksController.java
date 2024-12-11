package com.fun.lunch.controller;

import com.fun.lunch.service.impl.JwtTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/works")
public class WorksController {

    private final JwtTokenProvider jwtTokenProvider;

    public WorksController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/redirect")
    public String redirect() {

        String jwt = jwtTokenProvider.createAccessToken();

        System.out.println("jwt = " + jwt);

        return jwt;

    }
}
