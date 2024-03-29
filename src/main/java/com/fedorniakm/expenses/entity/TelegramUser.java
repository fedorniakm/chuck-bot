package com.fedorniakm.expenses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "user_id")
    private PlatformUser platformUser;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "current_group_id")
    private Group currentGroup;

    public void setPlatformUser(PlatformUser platformUser) {
        this.platformUser = platformUser;
        if (!this.platformUser.getTelegramUser().equals(this)) {
            this.platformUser.setTelegramUser(this);
        }
    }
}
