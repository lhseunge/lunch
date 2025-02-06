package com.fun.lunch.domain.works.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionAlertRequest {

    private String project;
    private String message;
    private String method;
    private String uri;
    private String stackTrace;
    private String userId;
}
