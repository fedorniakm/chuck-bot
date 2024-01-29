package com.fedorniakm.expenses.bot;

import com.fedorniakm.expenses.bot.handler.UpdateProcessor;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandlerChain {

    void process(Update update);

    void addProcessor(UpdateProcessor processor);

    void addProcessors(UpdateProcessor... processors);

}
