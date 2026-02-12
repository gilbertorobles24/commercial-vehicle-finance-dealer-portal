package com.dealerfinance.loan.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateLoanApplicationRequest {

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    @NotBlank(message = "Buyer name is required")
    private String buyerName;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @Min(value = 1, message = "Term months must be at least 1")
    private Integer termMonths;
}