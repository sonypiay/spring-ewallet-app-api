package com.ewallet.app.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWalletRequest {

    @NotBlank(message = "account name must be required")
    private String accountName;

    @NotBlank(message = "account number must be required")
    private String accountNumber;
}
