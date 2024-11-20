package com.ewallet.app.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customers")
@Table(
        name = "customers",
        uniqueConstraints = {
                @UniqueConstraint(name = "email", columnNames = "email")
        }
)
public class Customers {

    @Id
    @Column(length = 36, updatable = false, nullable = false)
    private String id;

    @Column(name = "first_name", length = 128, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 128)
    private String lastName;

    @Column(length = 128, nullable = false)
    private String email;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Column(columnDefinition = "boolean", nullable = false)
    private boolean active = true;

    @Column(columnDefinition = "datetime", updatable = false, nullable = false)
    private Date createdAt;

    @Column(columnDefinition = "datetime")
    private Date updatedAt;

    @OneToOne(mappedBy = "customers")
    private Wallets wallets;

    @OneToOne(mappedBy = "customers")
    private PersonalTokens personalTokens;
}
