package com.fedorniakm.expenses.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor
public class UpdateProcessor {

    private final AbsSender sender;

    public void process(Update update) {
        System.out.println("Message" + update.getMessage().getText());

        try {
            var response = new SendMessage();
            response.setText("Received.");
            response.setChatId(update.getMessage().getChatId());
            sender.execute(new SendMessage());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
