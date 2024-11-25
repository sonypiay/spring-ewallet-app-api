package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CustomersRepository extends JpaRepository<Customers, String> {

    @Query("select c from customers as c where email = :email")
    Optional<Customers> findByEmail(@Param("email") String email);

    @Query("select c from customers as c where c.id = :customerId AND c.personalTokens.accessToken = :accessToken")
    Optional<Customers> findByAccessToken(@Param("customerId") String customerId, @Param("accessToken") String accessToken);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, String id);
}
