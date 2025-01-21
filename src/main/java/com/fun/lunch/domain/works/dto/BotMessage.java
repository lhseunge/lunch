package com.fun.lunch.domain.works.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BotMessage<T> {

    private T content;

    public static BotMessage<?> text(String text) {
        return new BotMessage<>(new BotMessageText(text));
    }

    public static BotMessage<?> sticker(String packageId, String stickerId) {
        return new BotMessage<>(new BotMessageSticker(packageId, stickerId));
    }

    public static BotMessage<?> sticker(String[] stickerArray) {
        return new BotMessage<>(new BotMessageSticker(stickerArray[1], stickerArray[0]));

    }

    public static BotMessage<?> button(String text, List<BotMessageButtonAction> actions) {
        return new BotMessage<>(new BotMessageButton(text, actions));
    }


}
