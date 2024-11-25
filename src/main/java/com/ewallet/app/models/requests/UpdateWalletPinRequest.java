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
public class UpdateWalletPinRequest {

    @NotBlank(message = "Please enter your old PIN number")
    @Size(min = 1, max = 6)
    private String oldPin;

    @NotBlank(message = "Please enter your PIN number")
    @Size(min = 1, max = 6)
    private String newPin;

    @NotBlank(message = "Please re-enter your PIN number")
    @Size(min = 1, max = 6)
    private String confirmPin;
}
