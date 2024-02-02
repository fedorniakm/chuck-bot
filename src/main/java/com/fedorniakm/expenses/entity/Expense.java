package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = {"group", "expenseCategory", "user"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory expenseCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "note")
    private String note;

    @Column(name = "expense_date")
    private LocalDateTime expenseDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public void setGroup(Group group) {
        this.group = group;
        this.group.getExpenses().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        this.user.getExpenses().add(this);
    }
}
