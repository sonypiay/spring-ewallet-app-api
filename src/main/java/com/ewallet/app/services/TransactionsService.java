package com.ewallet.app.services;

import com.ewallet.app.exceptions.NotFoundException;
import com.ewallet.app.models.entities.Transactions;
import com.ewallet.app.models.entities.Wallets;
import com.ewallet.app.models.enums.TransactionsType;
import com.ewallet.app.models.repositories.CustomersRepository;
import com.ewallet.app.models.repositories.TransactionsRepository;
import com.ewallet.app.models.repositories.WalletsRepository;
import com.ewallet.app.models.requests.DepositRequest;
import com.ewallet.app.models.responses.DepositResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private WalletsRepository walletsRepository;

    @Transactional
    public DepositResponse deposit(DepositRequest request, String customerId) {
        Wallets getWallet = walletsRepository.findByAccountNumberAndCustomersForUpdate(request.getAccountNumber(), customerId)
                .orElseThrow(() -> new NotFoundException("Nomor akun tidak ditemukan"));

        Random random = new Random();
        String trxNumber = String.valueOf(random.nextLong(100000000000000L));
        Long currentBalance = getWallet.getBalance();
        Long totalBalance = currentBalance + request.getAmount();

        getWallet.setBalance(totalBalance);

        Transactions transactions = new Transactions();
        transactions.setId(UUID.randomUUID().toString());
        transactions.setTrxNumber(trxNumber);
        transactions.setAmount(request.getAmount());
        transactions.setTransactionsType(TransactionsType.IN.name().toLowerCase());
        transactions.setNotes(request.getNotes());
        transactions.setWallets(getWallet);
        transactions.setCreatedAt(new Date());

        transactionsRepository.save(transactions);
        walletsRepository.save(getWallet);

        return DepositResponse.builder()
                .balance(totalBalance)
                .trxNumber(transactions.getTrxNumber())
                .notes(transactions.getNotes())
                .build();
    }
}
