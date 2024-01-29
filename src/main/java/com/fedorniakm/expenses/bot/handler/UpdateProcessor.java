package com.fedorniakm.expenses.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

public abstract class UpdateProcessor implements Processor<Update, AbsSender> {

    @Override
    public void process(final Update update, final AbsSender sender) {
        if (isProcessable(update)) {
            processCurrent(update, sender);
        }
        next().ifPresent(processor -> processor.process(update, sender));
    }

    protected abstract Optional<UpdateProcessor> next();

    protected abstract void processCurrent(final Update update, final AbsSender sender);

    protected abstract boolean isProcessable(final Update update);

}
