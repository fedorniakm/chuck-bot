package com.fedorniakm.expenses.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

public interface Processor<T> {

    void process(final T input);

}
