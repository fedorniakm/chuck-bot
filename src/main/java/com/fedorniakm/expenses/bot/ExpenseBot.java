package com.fedorniakm.expenses.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class ExpenseBot extends TelegramLongPollingBot {

    private final String botUsername;

    private final Executor executor;

    private final HandlerChain handlerChain;

    public ExpenseBot(DefaultBotOptions options,
                      @Value("${bot-token}")String botToken,
                      @Value("${bot-username}") String botUsername,
                      HandlerChain handlerChain) {
        super(options, botToken);
        this.botUsername = botUsername;
        this.handlerChain = handlerChain;
        this.executor = Executors.newCachedThreadPool();
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        executor.execute(
                () -> handlerChain.process(update)
        );
    }

}
