package com.dealerfinance.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dealerfinance.loan.domain.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
}