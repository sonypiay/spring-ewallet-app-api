package com.ewallet.app.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {

    private String id;

    private Long balance = 0L;

    private String accountNumber;

    private String accountName;

    private boolean active;

    private Date createdAt;

    private Date updatedAt;
}
