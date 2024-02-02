package com.fedorniakm.expenses.bot.handler;

import com.fedorniakm.expenses.bot.ResponseService;
import com.fedorniakm.expenses.bot.util.UserUtil;
import com.fedorniakm.expenses.service.TelegramUserService;
import com.fedorniakm.expenses.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class StartCommandHandler extends UpdateHandler {

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

    private final UserService userService;

    private final TelegramUserService telegramUserService;

    protected StartCommandHandler(ResponseService responseService, UserService userService, TelegramUserService telegramUserService) {
        super(responseService);
        this.userService = userService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    protected void handleCurrent(Update update) {
        log.info("StartCommandProcessor -> processing /start command");

        var tgUser = UserUtil.mapUser(update.getMessage().getFrom());
        var isFirstVisit = telegramUserService.existsById(tgUser.getId());

        var chatId = update.getMessage().getChatId();
        response.sendMessage(chatId, GREETING_MESSAGE);

        if (isFirstVisit) {
            tgUser = telegramUserService.createNew(tgUser);
            //TODO: init new user with default group and category
            response.sendMessage(chatId, "Схоже ти тут вперше. " +
                    "В такому разі, я на*Чак*лував тобі нову групу \"Персональні витрати\" " +
                    "і категорію витрат - \"Інші\"." +
                    "Аби всі витрати не потрапляли в цю категорію, не соромся створити нові категорії! " +
                    "Використай для цього команду /newcategory");
        } else {
            tgUser = telegramUserService.save(tgUser);
            var responseText = new StringBuilder("Схоже ти тут вже бував!");
            responseText.append("Твоя поточна група: ")
                    .append(tgUser.getCurrentGroup().getName())
                    .append("\n");
            var groups = tgUser.getUser().getGroups();

            if (groups.size() > 1) {
                responseText.append("Зазначу, що у тебе є ще групи, ось всі:\n");
                groups.forEach(g -> responseText
                        .append("- ")
                        .append(g.getName())
                        .append("\n"));
            }

            response.sendMessage(chatId, responseText.toString());
        }


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
