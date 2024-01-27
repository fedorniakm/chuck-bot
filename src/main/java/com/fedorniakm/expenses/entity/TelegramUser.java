package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "telegram_users")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class TelegramUser {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
        if (!this.user.getTelegramUser().equals(this)) {
            this.user.setTelegramUser(this);
        }
    }
}
