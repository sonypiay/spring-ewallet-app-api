package com.ewallet.app.models.requests;

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
public class ChangePasswordRequest {

    @NotBlank(message = "Please enter your old password")
    private String oldPassword;

    @NotBlank(message = "Please enter your new password")
    @Size(min = 8, message = "Password length must be at least 8 characters")
    private String newPassword;

    @NotBlank(message = "Please re-enter your password")
    private String confirmPassword;
}
