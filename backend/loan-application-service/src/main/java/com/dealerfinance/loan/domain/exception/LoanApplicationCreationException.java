package com.dealerfinance.loan.domain.exception;

public class LoanApplicationCreationException extends RuntimeException {
    public LoanApplicationCreationException(String message) {
        super(message);
    }

    public LoanApplicationCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}