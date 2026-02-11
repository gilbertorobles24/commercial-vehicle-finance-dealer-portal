package com.dealerfinance.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationDTO {
    private Long id;
    private Long dealerId;
    private String vehicleType;
    private String buyerName;
    private BigDecimal amount;
    private Integer termMonths;
    private String status;
    private List<String> statusHistory;
    private List<String> documents;
}