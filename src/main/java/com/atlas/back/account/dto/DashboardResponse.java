package com.atlas.back.account.dto;

import com.atlas.back.transaction.dto.TransactionResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardResponse {
    private Long accountId;
    private String accountNumber;
    private String ownerName;
    private String type;
    private BigDecimal balance;
    private String status;
    private List<TransactionResponse> recentTransactions;
}