package com.ewallet.app.models.repositories;

import com.ewallet.app.models.entities.Wallets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletsRepository extends JpaRepository<Wallets, String> {
}
