package com.fun.lunch.service;

import com.fun.lunch.dto.WorksAccessToken;

public interface WorksService {

    WorksAccessToken getAccessToken();

    void sendBotMessage();
}
