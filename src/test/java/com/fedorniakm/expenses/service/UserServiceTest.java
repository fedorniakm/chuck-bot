package com.fedorniakm.expenses.service;

import com.fedorniakm.expenses.entity.ExpenseCategory;
import com.fedorniakm.expenses.entity.Group;
import com.fedorniakm.expenses.entity.User;
import com.fedorniakm.expenses.repository.ExpenseCategoryRepository;
import com.fedorniakm.expenses.repository.GroupRepository;
import com.fedorniakm.expenses.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class UserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Test
    void createUser_CreateDefaultGroupAndCategory_DefaultsAreCreated() {
        var name = "John Doe";
        var user = service.createUser(name);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getGroups());
        assertEquals(name, user.getName());
        assertEquals(1, user.getGroups().size());
    }

    @Test
    void createUser_SavesUserWithGroupAndCategoryToDb_UserGroupCategoryAreSaved() {
        var name = "John Doe";
        var newUser = service.createUser(name);
        assertNotNull(newUser.getId());
        assertNotNull(newUser.getGroups());
        assertFalse(newUser.getGroups().isEmpty());
        assertTrue(userRepository.existsById(newUser.getId()));
        var optionalUser = userRepository.findById(newUser.getId());
        assertTrue(optionalUser.isPresent());
        var dbUser = optionalUser.get();
        assertNotNull(dbUser.getGroups());
        assertFalse(dbUser.getGroups().isEmpty());
    }

    @Test
    void delete_DeleteUser_UserIsDeleted() {
        var user = User.builder()
                .name("John Doe")
                .build();
        userRepository.save(user);
        var userId = user.getId();

        service.delete(user);

        assertFalse(userRepository.existsById(userId));
    }

    @Test
    void delete_DeleteUserWithGroups_GroupsAreDeletedRecursively() {
        var g1 = Group.builder().name("Group1").build();
        var g2 = Group.builder().name("Group2").build();
        var g3 = Group.builder().name("Group3").build();
        var groups = Set.of(g1, g2, g3);
        var user = User.builder()
                .name("John Doe")
                .groups(groups)
                .build();
        userRepository.save(user);
        var userId = user.getId();
        var g1Id = g1.getId();
        var g2Id = g2.getId();
        var g3Id = g3.getId();

        service.delete(user);

        assertFalse(userRepository.existsById(userId));
        assertFalse(groupRepository.existsById(g1Id));
        assertFalse(groupRepository.existsById(g2Id));
        assertFalse(groupRepository.existsById(g3Id));
    }

    @Test
    void delete_DeleteUserWithGroupExpenseCategories_CategoriesAreDeletedRecursively() {
        var c1 = ExpenseCategory.builder().name("Cate1").build();
        var c2 = ExpenseCategory.builder().name("Cate2").build();
        var c3 = ExpenseCategory.builder().name("Cate3").build();

        var group = Group.builder().name("Group").build();
        group.addExpenseCategory(c1);
        group.addExpenseCategory(c2);
        group.addExpenseCategory(c3);
        var user = User.builder().name("John Doe").build();
        user.addGroup(group);
        userRepository.save(user);

        var userId = user.getId();
        var c1Id = c1.getId();
        var c2Id = c2.getId();
        var c3Id = c3.getId();

        service.delete(user);

        assertFalse(userRepository.existsById(userId));
        assertFalse(expenseCategoryRepository.existsById(c1Id));
        assertFalse(expenseCategoryRepository.existsById(c2Id));
        assertFalse(expenseCategoryRepository.existsById(c3Id));
    }

}
