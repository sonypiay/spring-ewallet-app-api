package com.ewallet.app.models.requests;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequest {

    @NotNull(message = "Amount must be required")
    @Negative
    @Range(min = -1000000, max = 0)
    private Long amount = 0L;

    @NotBlank(message = "Account number must be required")
    private String accountNumber;
}
