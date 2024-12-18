package com.fun.lunch.domain.works.dto;

import lombok.Getter;

@Getter
public class BotMessageText extends AbstractBotMessage {
    private final String text;

    public BotMessageText(String text) {
        super("text");
        this.text = text;
    }
}
