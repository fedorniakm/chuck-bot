package com.fedorniakm.expenses.configuration;

import com.fedorniakm.expenses.bot.ExpenseBot;
import com.fedorniakm.expenses.bot.handler.StartCommandProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramLongPollingBot bot) throws TelegramApiException {
        var telegramApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramApi.registerBot(bot);
        return telegramApi;
    }

    @Bean
    public TelegramLongPollingBot telegramLongPollingBot(DefaultBotOptions botOptions,
                                                         @Value("${bot-token}") String botToken,
                                                         @Value("${bot-username}") String botUsername,
                                                         StartCommandProcessor startCommandHandler) {
        return new ExpenseBot(botOptions, botToken, botUsername, startCommandHandler);
    }

    @Bean
    public DefaultBotOptions botOptions() {
        return new DefaultBotOptions();
    }
}
