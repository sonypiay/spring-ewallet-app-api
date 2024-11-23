package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.Customers;
import com.ewallet.app.models.entities.Wallets;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WalletsRepository extends JpaRepository<Wallets, String>, JpaSpecificationExecutor<Specification> {

    boolean existsByCustomers(Customers customers);
    boolean existsByAccountNumber(String accountNumber);

    Optional<Wallets> findByAccountNumberAndCustomers(String accountNumber, Customers customers);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from wallets w where w.accountNumber = :accountNumber and w.customers.id = :customerId")
    Optional<Wallets> findByAccountNumberAndCustomersForUpdate(@Param("accountNumber") String accountNumber, @Param("customerId") String customers);

    List<Wallets> findAllByCustomers(Customers customers);
}