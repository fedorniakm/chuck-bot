package com.fedorniakm.expenses.bot.chain;

import com.fedorniakm.expenses.bot.handler.UpdateHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultHandlerChain implements HandlerChain{

    private final Set<UpdateHandler> processors;

    public DefaultHandlerChain() {
        processors = new HashSet<>();
    }

    public DefaultHandlerChain(UpdateHandler... processors) {
        this.processors = new HashSet<>();
        this.processors.addAll(List.of(processors));
    }

    @Override
    public void process(Update update) {
        processors.forEach(processor -> processor.handle(update));
    }

    @Override
    public void addProcessor(UpdateHandler processor) {
        processors.add(processor);
    }

    @Override
    public void addProcessors(UpdateHandler... processors) {
        this.processors.addAll(List.of(processors));
    }

}
