package com.fedorniakm.expenses.bot.handler;

import com.fedorniakm.expenses.bot.ResponseService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GroupCommandHandler extends UpdateHandler {

    private static final String COMMANDS = "groups|newgroup|deletegroup";

    protected GroupCommandHandler(ResponseService responseService) {
        super(responseService);
    }

    @Override
    protected void handleCurrent(Update update) {

    }

    @Override
    protected boolean isProcessable(Update update) {
        return update.hasMessage()
                && update.getMessage().isCommand()
                && isCategoryCommand(update.getMessage().getText());
    }

    private boolean isCategoryCommand(String input) {
        return input.matches("^\\/" + COMMANDS + ".*$");
    }
}
