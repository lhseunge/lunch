package com.fun.lunch.domain.works.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BotMessageButton extends AbstractBotMessage {
    private final String contentText;
    private final List<BotMessageButtonAction> actions;


    public BotMessageButton(String text, List<BotMessageButtonAction> actions) {
        super("button_template");
        this.contentText = text;
        this.actions = actions;
    }
}
