package com.ewallet.app.controllers;

import com.ewallet.app.models.requests.AuthRequest;
import com.ewallet.app.models.requests.RegisterRequest;
import com.ewallet.app.models.responses.AuthResponse;
import com.ewallet.app.models.responses.RegisterResponse;
import com.ewallet.app.services.AuthService;
import com.ewallet.app.utils.ApiResponseSuccess;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseSuccess<AuthResponse>> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.login(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.ok(ApiResponseSuccess.<AuthResponse>builder()
                .data(response)
                .message("Login success")
                .build()
        );
    }

    @PostMapping(
            path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseSuccess<RegisterResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = authService.register(registerRequest);

        return ResponseEntity.ok(ApiResponseSuccess.<RegisterResponse>builder()
                .data(registerResponse)
                .message("success")
                .build()
        );
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponseSuccess<String>> logout(@RequestHeader String Authorization) {
        authService.logout(authService.getToken(Authorization));

        return ResponseEntity.ok(ApiResponseSuccess.<String>builder()
                .message("Logout success")
                .build()
        );
    }
}
