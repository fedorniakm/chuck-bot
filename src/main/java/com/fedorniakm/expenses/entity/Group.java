package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "groups")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class Group {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(name = "group_members",
            joinColumns = @JoinColumn(name="group_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id"))
    private Set<User> members;

    @OneToMany(mappedBy = "group",
            cascade = ALL)
    private Set<Expense> expenses;

    @OneToMany(mappedBy = "group",
            cascade = ALL)
    private Set<ExpenseCategory> expenseCategories;

    public void addExpense(Expense e) {
        expenses.add(e);
        e.setGroup(this);
    }

    public void addExpenses(Iterable<Expense> expenses) {
        expenses.forEach(e -> {
            e.setGroup(this);
            this.expenses.add(e);
        });
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
        this.expenses.forEach(e -> e.setGroup(this));
    }

    public void addMember(User member) {
        this.members.add(member);
        member.getGroups().add(this);
    }

    public void setMembers(Set<User> members) {
        this.members = members;
        members.forEach(member -> member.getGroups().add(this));
    }

    public void setCreator(User creator) {
        this.creator = creator;
        creator.getGroups().add(this);
    }

    public void addExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategories.add(expenseCategory);
        expenseCategory.setGroup(this);
    }
}
