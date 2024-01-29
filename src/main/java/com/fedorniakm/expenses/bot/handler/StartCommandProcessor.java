package com.fedorniakm.expenses.bot.handler;

import com.fedorniakm.expenses.bot.ResponseService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
@Slf4j
public class StartCommandProcessor extends UpdateProcessor {

    private static final String COMMAND = "start";

    private static final String GREETING_MESSAGE = """
            Привіт! Я Чак - обліковий собак. Я веду облік твоїх витрат. Гав!
            Ти можеш створювати Групи витрат для різних потреб та додавати туди інших користувачів за потреби. Наприклад:
            - група "Персональні витрати" для твоїх витрат на себе
            - чи група "Сім'я" куди ти можеш додати людей з якими бажаєш вести облік витрат разом.
            
            Також ти можеш додати різні категорії витрат для кожної, як от "Продукти", "Пальне" чи "Комунальні послуги".
            
            Ти можеш контролювати свої витрати надсилаючи ці команди:
            
            /groups - список всі твїх груп
            /newgroup [назва групи] - створити нову групу витрат
            /deletegroup [назва групи] - видалити групу
            
            /categories
            /newcategory [назва категорії] [ключові слова] - додає нову категорію витрат з ключовими словами до поточної групи
            /deletecategory [назва категорії] - видаляє категорію з поточної групи
            
            /exp [сума] [ключове слово] - додає витрату до категорії з ключовим словом
            
            /stat - статистика витрат
            """;

    protected StartCommandProcessor(ResponseService responseService) {
        super(responseService);
    }

    @Override
    protected void processCurrent(Update update) {
        log.info("StartCommandProcessor -> processing /start command");
        var chatId = update.getMessage().getChatId();
        response.sendMessage(chatId, GREETING_MESSAGE);
    }

    @Override
    protected boolean isProcessable(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && isStartCommand(update.getMessage().getText());
    }

    private boolean isStartCommand(String message) {
        return message.matches("^\\/" + COMMAND + ".*$");
    }
}
