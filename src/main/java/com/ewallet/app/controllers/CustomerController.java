package com.ewallet.app.controllers;

import com.ewallet.app.exceptions.UnauthorizedException;
import com.ewallet.app.models.requests.UpdateCustomerRequest;
import com.ewallet.app.models.responses.CustomersResponse;
import com.ewallet.app.services.AuthService;
import com.ewallet.app.services.CustomersService;
import com.ewallet.app.utils.ApiResponseSuccess;
import com.ewallet.app.utils.Base64EncodeDecode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomersService customersService;

    @GetMapping
    public ResponseEntity<ApiResponseSuccess<CustomersResponse>> showProfile(@RequestHeader("Authorization") String Authorization) {
        String parsingAuthorization = Authorization.replace("Basic ", "");
        String[] decodeToken = Base64EncodeDecode.decode(parsingAuthorization).split(":");
        String customerId = decodeToken[0];
        String accessToken = decodeToken[1];

        if( customerId == null && accessToken == null ) {
            throw new UnauthorizedException("Missing token");
        }

        CustomersResponse customersResponse = customersService.showProfile(customerId, accessToken);

        return ResponseEntity.ok(ApiResponseSuccess.<CustomersResponse>builder()
                .data(customersResponse)
                .message("success")
                .build()
        );
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseSuccess<CustomersResponse>> updateProfile(
            @Valid @RequestBody UpdateCustomerRequest request,
            @RequestHeader("Authorization") String Authorization
            )
    {
        String customerId = authService.getCustomerId(Authorization);
        CustomersResponse response = customersService.update(request, customerId);

        return ResponseEntity.ok(ApiResponseSuccess.<CustomersResponse>builder()
                .data(response)
                .message("success")
                .build()
        );
    }
}
