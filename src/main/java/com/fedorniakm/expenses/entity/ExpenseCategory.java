package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_categories")
@Setter
@Getter
@ToString
@EqualsAndHashCode(exclude = {"user", "group"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCategory {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "keywords_regex")
    private String keywordsRegex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private PlatformUser platformUser;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public void setGroup(Group group) {
        this.group = group;
        this.group.getExpenseCategories().add(this);
    }

}
