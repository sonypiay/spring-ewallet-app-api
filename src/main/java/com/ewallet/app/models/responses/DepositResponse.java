package com.ewallet.app.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponse {

    private Long balance = 0L;

    private String trxNumber;

    private String notes;
}
