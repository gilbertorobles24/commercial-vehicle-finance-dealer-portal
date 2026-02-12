package com.dealerfinance.loan.domain.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * Domain event published when a new loan application is successfully created.
 * 
 * This is an immutable event carrying the minimal data needed by downstream consumers 
 * (underwriting, notifications, audit, credit bureau integration, analytics, etc.).
 * 
 * Key design choices:
 * - Immutable (final fields, no setters)
 * - Self-validating constructor
 * - Serializable-friendly (simple types, no entity references)
 * - Includes creation timestamp for ordering and auditing
 * - Ready for event sourcing, Kafka, or message broker serialization
 */
@Getter
@ToString
@EqualsAndHashCode
public final class LoanApplicationCreatedEvent {

    private final Long loanId;
    private final Long dealerId;
    private final BigDecimal requestedAmount;
    private final Instant occurredAt;

    /**
     * Full constructor with validation.
     *
     * @param loanId         the generated ID of the new loan application
     * @param dealerId       the ID of the dealer who created the application
     * @param requestedAmount the requested loan amount
     * @param occurredAt     when the creation occurred (UTC)
     */
    public LoanApplicationCreatedEvent(
            Long loanId,
            Long dealerId,
            BigDecimal requestedAmount,
            Instant occurredAt) {

        this.loanId = Objects.requireNonNull(loanId, "loanId must not be null");
        this.dealerId = Objects.requireNonNull(dealerId, "dealerId must not be null");
        this.requestedAmount = Objects.requireNonNull(requestedAmount, "requestedAmount must not be null");
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");

        if (requestedAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("requestedAmount must be positive");
        }
    }

    /**
     * Factory method — preferred way to create the event.
     * Uses current time as occurredAt.
     */
    public static LoanApplicationCreatedEvent now(Long loanId, Long dealerId, BigDecimal requestedAmount) {
        return new LoanApplicationCreatedEvent(
                loanId,
                dealerId,
                requestedAmount,
                Instant.now()
        );
    }
}