package com.fedorniakm.expenses.service;

import com.fedorniakm.expenses.entity.ExpenseCategory;
import com.fedorniakm.expenses.entity.Group;
import com.fedorniakm.expenses.entity.User;
import com.fedorniakm.expenses.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private static final String CATEGORY_NAME_TEMPLATE = "Інше";
    private static final String GROUP_NAME_TEMPLATE = "Персональні витрати";

    private final UserRepository userRepository;

    public Optional<User> getUserById(final Long id) {
        return userRepository.findById(id);
    }

    public User createUser(String name) {
        var category = new ExpenseCategory();
        category.setName(CATEGORY_NAME_TEMPLATE);
        var group = new Group();
        group.setName(GROUP_NAME_TEMPLATE);
        var user = new User();
        user.setName(name);
        group.addExpenseCategory(category);
        user.addGroup(group);
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
