package com.dealerfinance.loan.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "loan_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long dealerId;

    private String vehicleType;

    private String buyerName;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    // Explicit public getter/setter – MapStruct/JPA needs public access
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private Integer termMonths;

    private String status;

    @ElementCollection
    private List<String> statusHistory;

    @ElementCollection
    private List<String> documents;
}