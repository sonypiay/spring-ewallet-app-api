package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CustomersRepository extends JpaRepository<Customers, String> {

    @Query("select c from customers as c where email = ?1")
    Optional<Customers> findByEmail(String email);
}
