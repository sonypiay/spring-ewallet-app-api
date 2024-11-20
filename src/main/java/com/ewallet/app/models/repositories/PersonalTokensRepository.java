package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.PersonalTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonalTokensRepository extends JpaRepository<PersonalTokens, String> {

    @Query("select u from personal_tokens u where u.customers.id = :customer_id")
    PersonalTokens findByCustomerId(@Param("customer_id") String customerId);
}
