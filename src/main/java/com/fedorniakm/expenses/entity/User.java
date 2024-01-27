package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(mappedBy = "user")
    private TelegramUser telegramUser;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "members",
            cascade = CascadeType.ALL)
    private Set<Group> groups;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    private Set<Expense> expenses;

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
        this.groups.forEach(
                group -> group.getMembers().add(this)
        );
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
        this.expenses.forEach(
                expense -> expense.setUser(this)
        );
    }

    public void setTelegramUser(TelegramUser telegramUser) {
        this.telegramUser = telegramUser;
        if (!this.telegramUser.getUser().equals(this)) {
            this.telegramUser.setUser(this);
        }
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        group.addMember(this);
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
        expense.setUser(this);
    }

}
