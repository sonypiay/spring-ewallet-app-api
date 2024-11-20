package com.ewallet.app.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "personal_tokens")
@Table(
        name = "personal_tokens",
        uniqueConstraints = {
                @UniqueConstraint(name = "access_token", columnNames = "access_token")
        }
)
public class PersonalTokens {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "access_token", nullable = false, length = 128)
    private String accessToken;

    @Column(name = "expired_at", nullable = false)
    private Long expiredAt;

    @Column(name = "created_at", nullable = false, columnDefinition = "datetime")
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ref_customer_id", referencedColumnName = "id")
    private Customers customers;
}
