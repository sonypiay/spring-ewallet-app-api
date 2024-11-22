package com.ewallet.app.controllers;

import com.ewallet.app.models.requests.SetWalletPinRequest;
import com.ewallet.app.models.responses.WalletResponse;
import com.ewallet.app.services.AuthService;
import com.ewallet.app.services.WalletsService;
import com.ewallet.app.utils.ApiResponseSuccess;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/wallets")
public class WalletsController {

    @Autowired
    private AuthService authService;

    @Autowired
    private WalletsService walletsService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseSuccess<WalletResponse>> create(
            @Valid @RequestBody SetWalletPinRequest request,
            @RequestHeader("Authorization") String Authorization)
    {
        String customerId = authService.getCustomerId(Authorization);
        WalletResponse response = walletsService.create(request, customerId);

        return ResponseEntity.ok(ApiResponseSuccess.<WalletResponse>builder()
                .data(response)
                .message("success")
                .build()
        );
    }

    @PostMapping(
            path = "/{accountNumber}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseSuccess<WalletResponse>> getWallet(
            @PathVariable("accountNumber") String accountNumber,
            @RequestHeader("Authorization") String Authorization,
            @Valid @RequestBody SetWalletPinRequest request)
    {
        String customerId = authService.getCustomerId(Authorization);
        WalletResponse response = walletsService.getWallet(accountNumber, customerId, request);

        return ResponseEntity.ok(ApiResponseSuccess.<WalletResponse>builder()
                .data(response)
                .message("success")
                .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseSuccess<List<WalletResponse>>> listWallet(
            @RequestHeader("Authorization") String Authorization
    ) {
        String customerId = authService.getCustomerId(Authorization);
        List<WalletResponse> response = walletsService.listWallet(customerId);

        return ResponseEntity.ok(ApiResponseSuccess.<List<WalletResponse>>builder()
                .data(response)
                .message("OK")
                .totalData(response.size())
                .build()
        );
    }
}
