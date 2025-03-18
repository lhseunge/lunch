package com.fun.lunch.domain.works.controller;

import com.fun.lunch.domain.works.dto.ExceptionAlertRequest;
import com.fun.lunch.domain.works.dto.WorksAccessToken;
import com.fun.lunch.domain.works.service.WorksService;
import com.fun.lunch.domain.works.service.impl.JwtTokenProvider;
import com.fun.lunch.domain.works.service.impl.WorksApi;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/works")
public class WorksController {

    private final JwtTokenProvider jwtTokenProvider;
    private final WorksService worksService;
    private final WorksApi worksApi;

    public WorksController(JwtTokenProvider jwtTokenProvider, WorksService worksService, WorksApi worksApi) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.worksService = worksService;
        this.worksApi = worksApi;
    }

    @GetMapping("/assertion")
    public String assertion() {

        return jwtTokenProvider.buildAssertionToken();

    }

    @GetMapping("/access-token")
    public WorksAccessToken getAccessToken() {
        return worksApi.generateToken();
    }

    @GetMapping("/lunch")
    public void sendLunchMessage() {
        worksService.sendTodayLunch();
    }

    @PostMapping("/exception")
    public void sendExceptionMessage(@RequestBody ExceptionAlertRequest exceptionAlertRequest) {
        worksService.sendException(exceptionAlertRequest);
    }
}
