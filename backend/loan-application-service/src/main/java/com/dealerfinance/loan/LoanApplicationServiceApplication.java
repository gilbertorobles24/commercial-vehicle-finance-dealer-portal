package com.dealerfinance.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main entry point for the Loan Application Service.
 *
 * This class bootstraps the Spring Boot application and serves as the root of component scanning.
 *
 * Features enabled:
 * - Auto-configuration
 * - Component scanning across all sub-packages
 * - Spring Boot production defaults (embedded Tomcat, actuator endpoints, etc.)
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.dealerfinance.loan")
public class LoanApplicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanApplicationServiceApplication.class, args);
    }
}