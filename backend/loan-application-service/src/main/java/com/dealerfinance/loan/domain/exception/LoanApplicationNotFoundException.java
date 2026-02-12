package com.dealerfinance.loan.domain.exception;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(Long id) {
        super("Loan application not found with id: " + id);
    }
}