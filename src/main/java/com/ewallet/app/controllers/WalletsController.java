package com.ewallet.app.controllers;

import com.ewallet.app.models.requests.CreateWalletRequest;
import com.ewallet.app.models.requests.SetWalletPinRequest;
import com.ewallet.app.models.requests.UpdateWalletPinRequest;
import com.ewallet.app.models.requests.UpdateWalletRequest;
import com.ewallet.app.models.responses.WalletResponse;
import com.ewallet.app.services.AuthService;
import com.ewallet.app.services.WalletsService;
import com.ewallet.app.utils.ApiResponseSuccess;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @Valid @RequestBody CreateWalletRequest request,
            @RequestHeader("Authorization") String Authorization)
    {
        String customerId = authService.getCustomerId(Authorization);
        WalletResponse response = walletsService.create(request, customerId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseSuccess.<WalletResponse>builder()
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

    @PatchMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseSuccess<WalletResponse>> updateWallet(
            @Valid @RequestBody UpdateWalletRequest request,
            @RequestHeader("Authorization") String Authorization
    ) {
        String customerId = authService.getCustomerId(Authorization);
        WalletResponse response = walletsService.updateWallet(request, customerId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseSuccess.<WalletResponse>builder()
                .data(response)
                .message("OK")
                .build()
        );
    }

    @PatchMapping(
            path = "/change-pin/{accountNumber}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseSuccess<WalletResponse>> changePin(
            @PathVariable("accountNumber") String accountNumber,
            @Valid @RequestBody UpdateWalletPinRequest pinRequest,
            @RequestHeader("Authorization") String Authorization
    ) {
        String customerId = authService.getCustomerId(Authorization);
        walletsService.changePin(pinRequest, accountNumber, customerId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseSuccess.<WalletResponse>builder()
                .message("Change pin success")
                .build()
        );
    }
}
