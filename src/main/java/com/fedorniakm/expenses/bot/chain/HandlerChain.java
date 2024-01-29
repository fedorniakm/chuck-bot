package com.fedorniakm.expenses.bot.chain;

import com.fedorniakm.expenses.bot.handler.UpdateHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandlerChain {

    void process(Update update);

    void addProcessor(UpdateHandler processor);

    void addProcessors(UpdateHandler... processors);

}
