package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "groups")
@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = {"members", "expenses", "expenseCategories"})
@Builder
public class Group {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private PlatformUser creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_members",
            joinColumns = @JoinColumn(name="group_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id"))
    @NotNull
    private Set<PlatformUser> members = new HashSet<>();

    @OneToMany(mappedBy = "group",
            cascade = ALL,
            fetch = FetchType.EAGER)
    @NotNull
    private Set<Expense> expenses = new HashSet<>();

    @OneToMany(mappedBy = "group",
            cascade = ALL,
            fetch = FetchType.EAGER)
    @NotNull
    private Set<ExpenseCategory> expenseCategories = new HashSet<>();

    public Group() {}

    public Group(Long id, String name, PlatformUser creator, Set<PlatformUser> members, Set<Expense> expenses, Set<ExpenseCategory> expenseCategories) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.members = Objects.requireNonNullElseGet(members, HashSet::new);
        this.expenses = Objects.requireNonNullElseGet(expenses, HashSet::new);
        this.expenseCategories = Objects.requireNonNullElseGet(expenseCategories, HashSet::new);
    }

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

    public void addMember(PlatformUser member) {
        this.members.add(member);
        member.getGroups().add(this);
    }

    public void setMembers(Set<PlatformUser> members) {
        this.members = members;
        members.forEach(member -> member.getGroups().add(this));
    }

    public void setCreator(PlatformUser creator) {
        this.creator = creator;
        this.members.add(creator);
        creator.getGroups().add(this);
    }

    public void addExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategories.add(expenseCategory);
        expenseCategory.setGroup(this);
    }
}
