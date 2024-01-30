package com.fedorniakm.expenses.bot.util;

import com.fedorniakm.expenses.entity.TelegramUser;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Objects;

public class UserUtil {

    public static TelegramUser mapUser(User apiUser) {
        Objects.requireNonNull(apiUser);
        return TelegramUser.builder()
                .id(apiUser.getId())
                .firstName(apiUser.getFirstName())
                .lastName(apiUser.getLastName())
                .username(apiUser.getUserName())
                .languageCode(apiUser.getLanguageCode())
                .build();
    }

}
