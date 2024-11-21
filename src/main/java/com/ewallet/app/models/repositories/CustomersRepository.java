package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CustomersRepository extends JpaRepository<Customers, String> {

    @Query("select c from customers as c where email = ?1")
    Optional<Customers> findByEmail(String email);

    @Query("select c from customers as c where c.id = ?1 AND c.personalTokens.accessToken = ?2")
    Optional<Customers> findByAccessToken(String customerId, String accessToken);

    boolean existsByEmail(@Param("email") String email);
}
