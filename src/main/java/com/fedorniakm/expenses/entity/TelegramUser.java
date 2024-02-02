package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "telegram_users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class TelegramUser {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "language_code")
    private String languageCode;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "current_group_id")
    private Group currentGroup;

    public void setUser(User user) {
        this.user = user;
        if (!this.user.getTelegramUser().equals(this)) {
            this.user.setTelegramUser(this);
        }
    }
}
