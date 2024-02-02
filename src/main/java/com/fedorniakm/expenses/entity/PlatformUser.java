package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = {"groups", "expenses"})
@Builder
public class PlatformUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(mappedBy = "user")
    private TelegramUser telegramUser;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToMany(mappedBy = "members",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @NotNull
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @NotNull
    private Set<Expense> expenses = new HashSet<>();

    public PlatformUser() { }

    public PlatformUser(Long id, TelegramUser telegramUser, String name, LocalDateTime createDate, Set<Group> groups, Set<Expense> expenses) {
        this.id = id;
        this.telegramUser = telegramUser;
        this.name = name;
        this.createDate = Objects.requireNonNullElseGet(createDate, LocalDateTime::now);
        setGroups(Objects.requireNonNullElseGet(groups, HashSet::new));
        setExpenses(Objects.requireNonNullElseGet(expenses, HashSet::new));
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
        this.groups.forEach(
                group -> group.getMembers().add(this)
        );
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
        this.expenses.forEach(
                expense -> expense.setPlatformUser(this)
        );
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        this.telegramUser = telegramUser;
        if (!this.telegramUser.getPlatformUser().equals(this)) {
            this.telegramUser.setPlatformUser(this);
        }
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        group.addMember(this);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
        expense.setPlatformUser(this);
    }

}
