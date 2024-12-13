package com.fun.lunch.dto;

import lombok.Getter;

@Getter
public class BotMessageSticker extends AbstractBotMessage {
    private final String packageId;
    private final String stickerId;

    public BotMessageSticker(String packageId, String stickerId) {
        super("sticker");
        this.packageId = packageId;
        this.stickerId = stickerId;
    }
}
