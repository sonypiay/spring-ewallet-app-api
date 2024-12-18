package com.ewallet.app.services;

import com.ewallet.app.exceptions.BadRequestException;
import com.ewallet.app.exceptions.NotFoundException;
import com.ewallet.app.models.entities.Customers;
import com.ewallet.app.models.entities.Wallets;
import com.ewallet.app.models.repositories.CustomersRepository;
import com.ewallet.app.models.repositories.WalletsRepository;
import com.ewallet.app.models.requests.CreateWalletRequest;
import com.ewallet.app.models.requests.SetWalletPinRequest;
import com.ewallet.app.models.requests.UpdateWalletPinRequest;
import com.ewallet.app.models.requests.UpdateWalletRequest;
import com.ewallet.app.models.responses.WalletResponse;
import com.ewallet.app.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WalletsService {

    @Autowired
    private WalletsRepository walletsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    public WalletResponse toResponse(Wallets wallets) {
        return WalletResponse.builder()
                .id(wallets.getId())
                .accountNumber(wallets.getAccountNumber())
                .accountName(wallets.getAccountName())
                .balance(wallets.getBalance())
                .active(wallets.isActive())
                .createdAt(wallets.getCreatedAt())
                .updatedAt(wallets.getUpdatedAt())
                .build();
    }

    @Transactional
    public WalletResponse create(CreateWalletRequest request, String customerId) {
        Customers customers = customersRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("User tidak ditemukan"));

        boolean confirmPinCode = request.getConfirmPin().equals(request.getPin());

        if( ! confirmPinCode ) throw new BadRequestException("Nomor pin salah");

        Random random = new Random();
        String accountNumber = String.valueOf(random.nextLong(10000000000L));
        String hashPinCode = BCrypt.hashpw(request.getPin(), BCrypt.gensalt());

        Wallets wallets = new Wallets();
        wallets.setId(UUID.randomUUID().toString());
        wallets.setBalance(0L);
        wallets.setCreatedAt(new Date());
        wallets.setAccountNumber(accountNumber);
        wallets.setAccountName(request.getAccountName());
        wallets.setPinCode(hashPinCode);
        wallets.setUpdatedAt(new Date());
        wallets.setActive(true);
        wallets.setCustomers(customers);

        walletsRepository.save(wallets);
        return toResponse(wallets);
    }

    public WalletResponse getWallet(String accountNumber, String customerId, SetWalletPinRequest request) {
        Customers customers = customersRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("User tidak ditemukan"));

        Wallets wallets = walletsRepository.findByAccountNumberAndCustomers(accountNumber, customers)
                .orElseThrow(() -> new NotFoundException("Nomor akun tidak ditemukan"));

        boolean confirmPin = request.getConfirmPin().equals(request.getPin());

        if(!confirmPin) throw new BadRequestException("Nomor pin anda salah!");

        return toResponse(wallets);
    }

    public List<WalletResponse> listWallet(String customerId) {
        Customers customers = customersRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("User tidak ditemukan"));

        List<Wallets> wallets = walletsRepository.findAllByCustomers(customers);
        return wallets.stream().map(this::toResponse).toList();
    }

    @Transactional
    public WalletResponse updateWallet(UpdateWalletRequest request, String customerId) {
        Customers customers = customersRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("User tidak ditemukan"));

        Wallets wallets = walletsRepository.findByAccountNumberAndCustomersForUpdate(request.getAccountNumber(), customerId)
                .orElseThrow(() -> new NotFoundException("Wallet tidak ditemukan"));

        wallets.setUpdatedAt(new Date());
        wallets.setAccountName(request.getAccountName());
        wallets.setCustomers(customers);
        walletsRepository.save(wallets);

        return toResponse(wallets);
    }

    @Transactional
    public void changePin(UpdateWalletPinRequest pinRequest, String accountNumber, String customerId) {
        Wallets wallets = walletsRepository.findByAccountNumberAndCustomersForUpdate(accountNumber, customerId)
                .orElseThrow(() -> new NotFoundException("Wallet tidak ditemukan"));

        boolean confirmPin = pinRequest.getConfirmPin().equals(pinRequest.getNewPin());
        boolean verifyOldPin = BCrypt.checkpw(pinRequest.getOldPin(), wallets.getPinCode());

        if( ! verifyOldPin ) throw new BadRequestException("Nomor pin lama anda salah");
        if( ! confirmPin ) throw new BadRequestException("Nomor pin anda salah");

        String hashPinCode = BCrypt.hashpw(pinRequest.getNewPin(), BCrypt.gensalt());
        wallets.setPinCode(hashPinCode);
        walletsRepository.save(wallets);
    }
}
