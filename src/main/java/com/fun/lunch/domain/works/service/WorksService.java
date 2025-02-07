package com.fun.lunch.domain.works.service;

import com.fun.lunch.domain.works.dto.ExceptionAlertRequest;

public interface WorksService {

    void sendTodayLunch();

    void sendException(ExceptionAlertRequest exceptionAlertRequest);
}
