package com.fedorniakm.expenses.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

public interface UpdateProcessor {

    default void process(final Update update, final AbsSender sender) {
        if (isProcessable(update)) {
            processCurrent(update, sender);
        }
        next().ifPresent(processor -> processor.process(update, sender));
    }

    Optional<UpdateProcessor> next();

    void processCurrent(final Update update, final AbsSender sender);

    boolean isProcessable(final Update update);

}
