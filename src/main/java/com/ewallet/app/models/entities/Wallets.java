package com.ewallet.app.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "wallets")
@Table(
        name = "wallets",
        uniqueConstraints = {
                @UniqueConstraint(name = "account_number", columnNames = {"account_number", "ref_customer_id"})
        },
        indexes = {
                @Index(name = "wallets_customer_id_foreign", columnList = "ref_customer_id")
        }
)
public class Wallets {

    @Id
    @Column(length = 36, updatable = false, nullable = false)
    private String id;

    @Column(length = 20, columnDefinition = "bigint", nullable = false)
    private Long balance;

    @Column(name = "account_number", length = 30, nullable = false, updatable = false)
    private String accountNumber;

    @Column(name = "pin_code", length = 64, nullable = false)
    private String pinCode;

    @Column(columnDefinition = "boolean", nullable = false)
    private boolean active = true;

    @Column(columnDefinition = "datetime", updatable = false, nullable = false)
    private Date createdAt;

    @Column(columnDefinition = "datetime")
    private Date updatedAt;

    @OneToOne
    @JoinColumn(
            name = "ref_customer_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "wallets_customer_id_foreign")
    )
    private Customers customers;

    @OneToMany(mappedBy = "wallets")
    private List<Transactions> transactionsList;
}
