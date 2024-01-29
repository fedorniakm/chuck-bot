package com.fedorniakm.expenses.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class ResponseService {

    private DefaultAbsSender sender;

    @Autowired
    public void setSender(@Lazy DefaultAbsSender sender) {
        this.sender = sender;
    }

    public void sendMessage(Long chatId, String text) {
        try {
            sender.execute(SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            log.error("ResponseSender -> failed to send message", e);
        }
    }

}




