package com.fedorniakm.expenses.bot.handler;
import com.fedorniakm.expenses.bot.ResponseService;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public abstract class UpdateProcessor implements Processor<Update> {

    protected final ResponseService response;

    @Setter
    protected UpdateProcessor nextProcessor;

    protected UpdateProcessor(ResponseService responseService) {
        response = responseService;
    }

    @Override
    public void process(final Update update) {
        if (isProcessable(update)) {
            processCurrent(update);
        }
        next().ifPresent(processor -> processor.process(update));
    }

    protected Optional<UpdateProcessor> next() {
        return Optional.ofNullable(nextProcessor);
    }

    protected abstract void processCurrent(final Update update);

    protected abstract boolean isProcessable(final Update update);

}
