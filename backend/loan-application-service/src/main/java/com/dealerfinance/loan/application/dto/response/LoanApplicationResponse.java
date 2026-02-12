package com.dealerfinance.loan.application.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LoanApplicationResponse {

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