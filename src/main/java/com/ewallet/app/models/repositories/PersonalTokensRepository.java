package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.PersonalTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonalTokensRepository extends JpaRepository<PersonalTokens, String> {
    @Modifying
    @Query("delete from personal_tokens where accessToken = :accessToken")
    void deleteByAccessToken(@Param("accessToken") String accessToken);

    @Query("select pt from personal_tokens as pt where pt.accessToken = :accessToken and pt.customers.id = :customerId")
    Optional<PersonalTokens> findByAccessTokenAndCustomerId(@Param("accessToken") String accessToken, @Param("customerId") String customerId);
}
