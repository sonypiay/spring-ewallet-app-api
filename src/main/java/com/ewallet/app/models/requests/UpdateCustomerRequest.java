package com.ewallet.app.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {

    @NotBlank(message = "first name must be required")
    private String firstName;

    private String lastName;

    @NotBlank(message = "email must be required")
    @Email
    private String email;

    private String phoneNumber;
}