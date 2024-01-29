package com.fedorniakm.expenses.configuration;

import com.fedorniakm.expenses.bot.chain.DefaultHandlerChain;
import com.fedorniakm.expenses.bot.chain.HandlerChain;
import com.fedorniakm.expenses.bot.handler.UpdateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfiguration {

    @Bean
    public DefaultBotOptions botOptions() {
        return new DefaultBotOptions();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramLongPollingBot bot) throws TelegramApiException {
        var telegramApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramApi.registerBot(bot);
        return telegramApi;
    }

    @Bean
    public HandlerChain handlerChain(UpdateHandler... processors) {
        return new DefaultHandlerChain(processors);
    }


}
