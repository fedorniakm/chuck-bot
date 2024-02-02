package com.fedorniakm.expenses.service;

import com.fedorniakm.expenses.entity.ExpenseCategory;
import com.fedorniakm.expenses.entity.Group;
import com.fedorniakm.expenses.entity.TelegramUser;
import com.fedorniakm.expenses.entity.User;
import com.fedorniakm.expenses.repository.TelegramUserRepository;
import com.fedorniakm.expenses.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TelegramUserService {


    private final UserService userService;

    private final TelegramUserRepository tgUserRepo;

    public boolean existsById(Long tgUserId) {
        return tgUserRepo.existsById(tgUserId);
    }

    public TelegramUser create(TelegramUser tgUser) {
        Objects.requireNonNull(tgUser);
        var userName = tgUser.getFirstName() + " " + tgUser.getLastName();
        var user = userService.createUser(userName);

        tgUser.setUser(user);

        tgUserRepo.save(tgUser);
        return tgUserRepo.save(tgUser);
    }

    public TelegramUser save(TelegramUser tgUser) {
        return tgUserRepo.save(tgUser);
    }
}
