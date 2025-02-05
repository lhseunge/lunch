package com.fun.lunch.domain.works.service;

import com.fun.lunch.domain.works.dto.WorksAccessToken;

public interface WorksService {

    WorksAccessToken getAccessToken();

    void sendTodayLunch();

    void sendException();
}
