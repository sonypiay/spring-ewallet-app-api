package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, String> {
}
