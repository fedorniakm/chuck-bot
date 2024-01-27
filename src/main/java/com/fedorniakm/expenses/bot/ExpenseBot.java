package com.fedorniakm.expenses.bot;

import lombok.Setter;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class ExpenseBot extends TelegramLongPollingBot {

    private final String botUsername;

    @Setter
    private UpdateProcessor updateProcessor;

    public ExpenseBot(DefaultBotOptions options, String botToken, String botUsername) {
        super(options, botToken);
        this.botUsername = botUsername;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateProcessor.process(update);
    }


}
