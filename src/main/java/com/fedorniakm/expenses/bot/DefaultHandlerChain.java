package com.fedorniakm.expenses.bot;

import com.fedorniakm.expenses.bot.handler.UpdateProcessor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultHandlerChain implements HandlerChain{

    private final Set<UpdateProcessor> processors;

    public DefaultHandlerChain() {
        processors = new HashSet<>();
    }

    public DefaultHandlerChain(UpdateProcessor... processors) {
        this.processors = new HashSet<>();
        this.processors.addAll(List.of(processors));
    }

    @Override
    public void process(Update update) {
        processors.forEach(processor -> processor.process(update));
    }

    @Override
    public void addProcessor(UpdateProcessor processor) {
        processors.add(processor);
    }

    @Override
    public void addProcessors(UpdateProcessor... processors) {
        this.processors.addAll(List.of(processors));
    }

}
