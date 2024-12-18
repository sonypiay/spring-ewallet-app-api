package com.ewallet.app.services;

import com.ewallet.app.exceptions.BadRequestException;
import com.ewallet.app.exceptions.UnauthorizedException;
import com.ewallet.app.models.entities.PersonalTokens;
import com.ewallet.app.models.repositories.PersonalTokensRepository;
import com.ewallet.app.models.entities.Customers;
import com.ewallet.app.models.repositories.CustomersRepository;
import com.ewallet.app.models.requests.RegisterRequest;
import com.ewallet.app.models.responses.AuthResponse;
import com.ewallet.app.models.responses.RegisterResponse;
import com.ewallet.app.security.BCrypt;
import com.ewallet.app.utils.Base64EncodeDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private PersonalTokensRepository personalTokensRepository;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        boolean isEmailExists = customersRepository.existsByEmail(request.getEmail());

        if(isEmailExists) {
            throw new BadRequestException("Email " + request.getEmail() + " sudah terdaftar");
        }

        String hashPassword = BCrypt.hashpw(
                request.getPassword(),
                BCrypt.gensalt()
        );

        Customers customers = new Customers();
        customers.setId(UUID.randomUUID().toString());
        customers.setFirstName(request.getFirstName());
        customers.setLastName(request.getLastName());
        customers.setEmail(request.getEmail());
        customers.setPhoneNumber(request.getPhoneNumber());
        customers.setPassword(hashPassword);
        customers.setCreatedAt(new Date());
        customers.setUpdatedAt(new Date());

        customersRepository.save(customers);

        return RegisterResponse.builder()
                .id(customers.getId())
                .email(customers.getEmail())
                .firstName(customers.getFirstName())
                .lastName(customers.getLastName())
                .phoneNumber(customers.getPhoneNumber())
                .build();
    }

    @Transactional
    public AuthResponse login(String email, String password) {
        Customers customers = customersRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Email atau password salah"));

        boolean verifyPassword = BCrypt.checkpw(password, customers.getPassword());

        if( ! verifyPassword ) throw new UnauthorizedException("Email atau password salah");

        String accessToken = UUID.randomUUID().toString();
        Long tokenExpiredAt = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30);

        PersonalTokens personalTokens = personalTokensRepository.findByCustomerId(customers.getId());

        if( personalTokens == null ) {
            personalTokens = new PersonalTokens();
        }

        personalTokens.setAccessToken(accessToken);
        personalTokens.setCreatedAt(new Date());
        personalTokens.setExpiredAt(tokenExpiredAt);
        personalTokens.setCustomers(customers);

        personalTokensRepository.save(personalTokens);

        String combineToken = customers.getId() + ":" + personalTokens.getAccessToken();
        String encodeAccessToken = Base64.getEncoder().encodeToString(combineToken.getBytes());

        return AuthResponse.builder()
                .accessToken(encodeAccessToken)
                .expiredAt(personalTokens.getExpiredAt())
                .build();
    }

    @Transactional
    public void logout(String accessToken) {
        personalTokensRepository.deleteByAccessToken(accessToken);
    }

    public String[] parsingAccessToken(String data) {
        String parsingAuthorization = data.replace("Basic ", "");
        return Base64EncodeDecode.decode(parsingAuthorization).split(":");
    }

    public String getToken(String data) {
        return this.parsingAccessToken(data)[1];
    }

    public String getCustomerId(String data) {
        return this.parsingAccessToken(data)[0];
    }
}
