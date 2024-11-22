package com.ewallet.app.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetWalletPinRequest {

    @NotBlank(message = "Please enter your PIN number")
    @Size(min = 1, max = 6)
    private String pin;

    @NotBlank(message = "Please re-enter your PIN number")
    @Size(min = 1, max = 6)
    private String confirmPin;
}
