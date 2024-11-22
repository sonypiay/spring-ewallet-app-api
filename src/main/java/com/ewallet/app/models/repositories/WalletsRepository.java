package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.Customers;
import com.ewallet.app.models.entities.Wallets;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface WalletsRepository extends JpaRepository<Wallets, String>, JpaSpecificationExecutor<Specification> {

    boolean existsByCustomers(Customers customers);
    boolean existsByAccountNumber(String accountNumber);
    Optional<Wallets> findByAccountNumberAndCustomers(String accountNumber, Customers customers);
    List<Wallets> findAllByCustomers(Customers customers);
}
