package com.ewallet.app.services;

import com.ewallet.app.exceptions.UnauthorizedException;
import com.ewallet.app.models.responses.CustomersResponse;
import com.ewallet.app.models.entities.Customers;
import com.ewallet.app.models.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    public CustomersResponse toResponse(Customers customers) {
        return CustomersResponse.builder()
                .id(customers.getId())
                .firstName(customers.getFirstName())
                .lastName(customers.getLastName())
                .email(customers.getEmail())
                .phoneNumber(customers.getPhoneNumber())
                .active(customers.isActive())
                .createdAt(customers.getCreatedAt())
                .updatedAt(customers.getUpdatedAt())
                .build();
    }

    public CustomersResponse showProfile(String customerId, String accessToken) {
        Customers customers = customersRepository.findByAccessToken(customerId, accessToken)
                .orElseThrow(() -> new UnauthorizedException("You are not logged in"));

        return toResponse(customers);
    }
}
