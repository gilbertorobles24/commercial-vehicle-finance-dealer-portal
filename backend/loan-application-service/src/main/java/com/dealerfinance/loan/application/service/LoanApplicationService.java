package com.dealerfinance.loan.application.service;

import com.dealerfinance.loan.application.dto.request.CreateLoanApplicationRequest;
import com.dealerfinance.loan.application.dto.response.LoanApplicationResponse;
import com.dealerfinance.loan.domain.event.LoanApplicationCreatedEvent;
import com.dealerfinance.loan.domain.exception.LoanApplicationCreationException;
import com.dealerfinance.loan.domain.exception.LoanApplicationNotFoundException;
import com.dealerfinance.loan.domain.model.LoanApplication;
import com.dealerfinance.loan.infrastructure.persistence.LoanApplicationRepository;
import com.dealerfinance.loan.interfaces.mapper.LoanApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Core service for loan application business logic.
 *
 * Responsibilities:
 * - Orchestrate creation, read, update, delete operations
 * - Apply business rules & validation
 * - Publish domain events
 * - Coordinate with repository & mapper
 *
 * This is the single source of truth for loan application lifecycle.
 */
@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository repository;
    private final LoanApplicationMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    // ────────────────────────────────────────────────
    //  CREATE - New loan application
    // ────────────────────────────────────────────────
    @Transactional
    public LoanApplicationResponse createApplication(
            CreateLoanApplicationRequest request,
            Long dealerId) {

        // Defensive guards (should be enforced earlier, but here for safety)
        Objects.requireNonNull(request, "Request must not be null");
        Objects.requireNonNull(dealerId, "Dealer ID must not be null");
        
        System.out.println("Received amount from request: " + request.getAmount());

        // Basic business validation (can be extracted to validator bean later)
        validateCreateRequest(request);

        // Map inbound request to domain entity
        LoanApplication entity = mapper.toEntity(request);
        System.out.println("Amount after mapping to entity: " + entity.getAmount());

        // Apply system/security context fields
        entity.setDealerId(dealerId);
        entity.setStatus("DRAFT");

        // Debug: see what we're about to save
        // System.out.println("Creating loan application: " + entity);

        // Persist
        LoanApplication saved;
        try {
            saved = repository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new LoanApplicationCreationException("Data integrity violation during creation", e);
        } catch (Exception e) {
            throw new LoanApplicationCreationException("Unexpected error during persistence", e);
        }

        System.out.println("Amount after save: " + saved.getAmount());
        
        // Publish domain event (decoupled side-effect for underwriting, notifications, etc.)
        eventPublisher.publishEvent(
                LoanApplicationCreatedEvent.now(
                        saved.getId(),
                        saved.getDealerId(),
                        saved.getAmount()
                )
        );

        // Map to outbound DTO
        return mapper.toResponse(saved);
    }

    private void validateCreateRequest(CreateLoanApplicationRequest request) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Requested amount must be positive");
        }
        if (request.getTermMonths() == null || request.getTermMonths() < 1) {
            throw new IllegalArgumentException("Term months must be at least 1");
        }
        if (request.getVehicleType() == null || request.getVehicleType().trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type is required");
        }
        // Add more business rules as needed
    }

    // ────────────────────────────────────────────────
    //  READ - All applications for a dealer
    // ────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findAllForDealer(Long dealerId) {
        Objects.requireNonNull(dealerId, "Dealer ID must not be null");

        return repository.findByDealerId(dealerId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // ────────────────────────────────────────────────
    //  READ - Single application by ID
    // ────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public Optional<LoanApplicationResponse> findById(Long id) {
        Objects.requireNonNull(id, "ID must not be null");

        return repository.findById(id)
                .map(mapper::toResponse);
    }

    // ────────────────────────────────────────────────
    //  UPDATE - Full replacement
    // ────────────────────────────────────────────────
    @Transactional
    public LoanApplicationResponse updateApplication(Long id, CreateLoanApplicationRequest request) {
        Objects.requireNonNull(id, "ID must not be null");
        Objects.requireNonNull(request, "Request must not be null");

        LoanApplication existing = repository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));

        // Map new data onto existing entity (or create new and copy ID)
        LoanApplication updated = mapper.toEntity(request);
        updated.setId(id);
        updated.setDealerId(existing.getDealerId()); // preserve ownership
        updated.setStatus(existing.getStatus());     // preserve current status

        // Future: merge logic for partial updates (PATCH)

        LoanApplication saved = repository.save(updated);
        return mapper.toResponse(saved);
    }

    // ────────────────────────────────────────────────
    //  DELETE
    // ────────────────────────────────────────────────
    @Transactional
    public void deleteApplication(Long id) {
        Objects.requireNonNull(id, "ID must not be null");

        if (!repository.existsById(id)) {
            throw new LoanApplicationNotFoundException(id);
        }

        repository.deleteById(id);

        // Future: publish LoanApplicationDeletedEvent
        // eventPublisher.publishEvent(new LoanApplicationDeletedEvent(id));
    }
}