package com.fedorniakm.expenses.bot;

import com.fedorniakm.expenses.bot.handler.StartCommandProcessor;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExpenseBot extends TelegramLongPollingBot {

    private final String botUsername;

    private final Executor executor;

    private final StartCommandProcessor startCommandProcessor;

    public ExpenseBot(DefaultBotOptions options, String botToken, String botUsername, StartCommandProcessor startCommandProcessor) {
        super(options, botToken);
        this.botUsername = botUsername;
        this.startCommandProcessor = startCommandProcessor;
        this.executor = Executors.newCachedThreadPool();
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        executor.execute(() -> startCommandProcessor.process(update, this));
    }

}
