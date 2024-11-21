package com.ewallet.app.services;

import com.ewallet.app.exceptions.BadRequestException;
import com.ewallet.app.exceptions.NotFoundException;
import com.ewallet.app.exceptions.UnauthorizedException;
import com.ewallet.app.models.requests.UpdateCustomerRequest;
import com.ewallet.app.models.responses.CustomersResponse;
import com.ewallet.app.models.entities.Customers;
import com.ewallet.app.models.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Transactional
    public CustomersResponse update(UpdateCustomerRequest request, String customerId) {
        Customers customers = customersRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Data not found"));

        boolean isEmailExists = customersRepository.existsByEmailAndIdNot(request.getEmail(), customerId);

        if(isEmailExists) {
            throw new BadRequestException("Email " + request.getEmail() + " sudah terdaftar");
        }

        customers.setFirstName(request.getFirstName());
        customers.setLastName(request.getLastName());
        customers.setEmail(request.getEmail());
        customers.setPhoneNumber(request.getPhoneNumber());
        customers.setUpdatedAt(new Date());

        customersRepository.save(customers);

        return toResponse(customers);
    }
}
