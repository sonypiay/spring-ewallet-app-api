package com.ewallet.app.controllers;

import com.ewallet.app.models.requests.DepositRequest;
import com.ewallet.app.models.responses.DepositResponse;
import com.ewallet.app.services.AuthService;
import com.ewallet.app.services.TransactionsService;
import com.ewallet.app.utils.ApiResponseSuccess;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    private AuthService authService;

    @Autowired
    private TransactionsService transactionsService;

    @PostMapping(
            path = "/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseSuccess<DepositResponse>> deposit(
            @Valid @RequestBody DepositRequest request,
            @RequestHeader("Authorization") String Authorization
    )
    {
        String customerId = authService.getCustomerId(Authorization);
        DepositResponse response = transactionsService.deposit(request, customerId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseSuccess.<DepositResponse>builder()
                        .data(response)
                        .message("Deposit success")
                        .build()
                );
    }
}
