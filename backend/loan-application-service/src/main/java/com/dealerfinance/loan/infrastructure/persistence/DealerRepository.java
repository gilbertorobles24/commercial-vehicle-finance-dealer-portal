package com.dealerfinance.loan.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dealerfinance.loan.domain.model.Dealer;

import java.util.Optional;

public interface DealerRepository extends JpaRepository<Dealer, Long> {
    Optional<Dealer> findByEmail(String email);
}
