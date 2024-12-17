package com.fun.lunch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BotMessageWrapper<T> {

    private T content;

    public static BotMessageWrapper<?> text(String text) {
        return new BotMessageWrapper<>(new BotMessageText(text));
    }

    public static BotMessageWrapper<?> sticker(String packageId, String stickerId) {
        return new BotMessageWrapper<>(new BotMessageSticker(packageId, stickerId));
    }

    public static BotMessageWrapper<?> sticker(String[] stickerArray) {
        return new BotMessageWrapper<>(new BotMessageSticker(stickerArray[1], stickerArray[0]));

    }

    public static BotMessageWrapper<?> button(String text, List<BotMessageButtonAction> actions) {
        return new BotMessageWrapper<>(new BotMessageButton(text, actions));
    }


}
