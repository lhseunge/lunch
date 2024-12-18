package com.fun.lunch.domain.works.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BotMessageButtonAction {

    private String type;
    private String label;
    private String uri;

}
