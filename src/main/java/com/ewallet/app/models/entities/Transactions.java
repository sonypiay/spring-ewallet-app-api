package com.ewallet.app.models.entities;

import com.ewallet.app.models.enums.TransactionsType;
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
@Entity(name = "transactions")
@Table(
        name = "transactions",
        uniqueConstraints = {
                @UniqueConstraint(name = "trx_number", columnNames = {"trx_number", "account_number"})
        },
        indexes = {
                @Index(name = "trx_account_number_foreign", columnList = "account_number"),
        }
)
public class Transactions {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "trx_number", length = 64, updatable = false)
    private String trxNumber;

    @Column(name = "type", columnDefinition = "enum")
    private String transactionsType;

    @Column(length = 20, nullable = false, updatable = false)
    private Long amount;

    @Column(length = 100)
    private String notes;

    @Column(nullable = false, updatable = false, columnDefinition = "datetime")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(
            name = "account_number",
            referencedColumnName = "account_number",
            foreignKey = @ForeignKey(name = "trx_account_number_foreign")
    )
    private Wallets wallets;
}
