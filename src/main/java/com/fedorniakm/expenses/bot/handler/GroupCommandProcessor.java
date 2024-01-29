package com.fedorniakm.expenses.bot.handler;

import com.fedorniakm.expenses.bot.ResponseService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GroupCommandProcessor extends UpdateProcessor{

    private static final String COMMANDS = "groups|newgroup|deletegroup";

    protected GroupCommandProcessor(ResponseService responseService) {
        super(responseService);
    }

    @Override
    protected void processCurrent(Update update) {

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
