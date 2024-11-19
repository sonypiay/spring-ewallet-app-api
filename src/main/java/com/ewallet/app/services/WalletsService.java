package com.ewallet.app.services;

import com.ewallet.app.models.repositories.WalletsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletsService {

    @Autowired
    private WalletsRepository repository;
}
