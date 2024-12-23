package com.fun.lunch.domain.works.controller;

import com.fun.lunch.domain.works.dto.WorksAccessToken;
import com.fun.lunch.domain.works.service.WorksService;
import com.fun.lunch.domain.works.service.impl.JwtTokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/works")
public class WorksController {

    private final JwtTokenProvider jwtTokenProvider;
    private final WorksService worksService;

    public WorksController(JwtTokenProvider jwtTokenProvider, WorksService worksService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.worksService = worksService;
    }

    @GetMapping("/assertion")
    public String assertion() {

        return jwtTokenProvider.buildAssertionToken();

    }

    @GetMapping("/access-token")
    public WorksAccessToken getAccessToken() {

        return worksService.getAccessToken();

    }

    @PostMapping("push")
    public void sendWorksBotMessage() {

        worksService.sendTodayLunch();

    }
}
