package com.fedorniakm.expenses.bot.handler;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class StartCommandProcessor implements UpdateProcessor {

    private static final String GREETING_MESSAGE = """
            Привіт! Я Чак - обліковий собак. Я веду облік твоїх витрат. Гав!
            Ти можеш створювати Групи витрат для різних потреб та додавати туди інших користувачів за потреби. Наприклад:
            - група "Персональні витрати" для твоїх витрат на себе
            - чи група "Сім'я" куди ти можеш додати людей з якими бажаєш вести облік витрат разом.
            
            Також ти можеш додати різні категорії витрат для кожної, як от "Продукти", "Пальне" чи "Комунальні послуги".
            
            Ти можеш контролювати свої витрати надсилаючи ці команди:
            
            /mygroups - список всі твїх груп
            /newgroup [назва групи] - створити нову групу витрат
            /deletegroup [назва групи] - видалити групу
            
            /categories
            /newcategory [назва категорії] [ключові слова] - додає нову категорію витрат з ключовими словами до поточної групи
            /deletecategory [назва категорії] - видаляє категорію з поточної групи
            
            /exp [сума] [ключове слово] - додає витрату до категорії з ключовим словом
            
            /stat - статистика витрат
            """;

    @Setter
    private UpdateProcessor nextProcessor;

    public StartCommandProcessor() {}

    public StartCommandProcessor(UpdateProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public Optional<UpdateProcessor> next() {
        return Optional.ofNullable(nextProcessor);
    }

    @Override
    public void processCurrent(Update update, AbsSender sender) {
        var response = new SendMessage();
        response.setChatId(update.getMessage().getChatId());
        response.setText(GREETING_MESSAGE);
        log.info("StartCommandProcessor -> processed start command");
        try {
            sender.execute(response);
        } catch (TelegramApiException e) {
            log.error("StartCommandProcessor -> exception handled", e);
        }
    }

    @Override
    public boolean isProcessable(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && isStartCommand(update.getMessage().getText());
    }

    private boolean isStartCommand(String message) {
        return message.matches("^\\/start.*$");
    }
}
