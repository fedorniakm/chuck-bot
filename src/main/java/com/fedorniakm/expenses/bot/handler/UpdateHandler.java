package com.fedorniakm.expenses.bot.handler;
import com.fedorniakm.expenses.bot.ResponseService;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public abstract class UpdateHandler implements Handler<Update> {

    protected final ResponseService response;

    @Setter
    protected UpdateHandler nextProcessor;

    protected UpdateHandler(ResponseService responseService) {
        response = responseService;
    }

    @Override
    public void handle(final Update update) {
        if (isProcessable(update)) {
            handleCurrent(update);
        }
        next().ifPresent(processor -> processor.handle(update));
    }

    protected Optional<UpdateHandler> next() {
        return Optional.ofNullable(nextProcessor);
    }

    protected abstract void handleCurrent(final Update update);

    protected abstract boolean isProcessable(final Update update);

}
