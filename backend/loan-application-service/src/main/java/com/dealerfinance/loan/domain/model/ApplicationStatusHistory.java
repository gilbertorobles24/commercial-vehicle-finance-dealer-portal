package com.dealerfinance.loan.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class ApplicationStatusHistory {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private LoanApplication loanApplication;

    private String status;
    private LocalDateTime changedAt = LocalDateTime.now();
    private String changedBy; // dealer email or system
}