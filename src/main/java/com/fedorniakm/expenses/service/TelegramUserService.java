package com.fedorniakm.expenses.service;

import com.fedorniakm.expenses.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserService {
    public boolean existsById(Long telegramUserId) {
        return false;
    }

    public TelegramUser init(TelegramUser telegramUser) {
        return null;
    }

    public TelegramUser synchronise(TelegramUser tgUser) {
        return null;
    }
}
