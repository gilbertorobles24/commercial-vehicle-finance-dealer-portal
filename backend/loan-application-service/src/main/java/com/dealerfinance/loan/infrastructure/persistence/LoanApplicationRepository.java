package com.dealerfinance.loan.infrastructure.persistence;

import com.dealerfinance.loan.domain.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for loan application persistence operations.
 */
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    /**
     * Finds all loan applications belonging to a specific dealer.
     *
     * @param dealerId the ID of the dealer
     * @return list of matching applications
     */
    List<LoanApplication> findByDealerId(Long dealerId);
}