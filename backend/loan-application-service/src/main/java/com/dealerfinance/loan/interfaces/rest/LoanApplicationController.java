package com.dealerfinance.loan.interfaces.rest;

import com.dealerfinance.loan.application.dto.request.CreateLoanApplicationRequest;
import com.dealerfinance.loan.application.dto.response.LoanApplicationResponse;
import com.dealerfinance.loan.application.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST API for loan application operations.
 * 
 * Supports full CRUD + partial update.
 * Security notes:
 * - All endpoints require authentication
 * - Future: add @PreAuthorize("hasRole('DEALER')") or ownership checks
 */
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService service;

    // ────────────────────────────────────────────────
    //  GET /api/applications
    //  List applications (filtered by dealer later)
    // ────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<LoanApplicationResponse>> listApplications(
            @AuthenticationPrincipal UserDetails user) {

        // TODO: extract dealerId from user context
        Long dealerId = 1L; // placeholder

        List<LoanApplicationResponse> applications = service.findAllForDealer(dealerId);

        return ResponseEntity.ok(applications);
    }

    // ────────────────────────────────────────────────
    //  GET /api/applications/{id}
    //  Get single application by ID
    // ────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> getApplicationById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {

        // TODO: ownership check (dealerId matches)
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ────────────────────────────────────────────────
    //  POST /api/applications
    //  Create new loan application
    // ────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<LoanApplicationResponse> createApplication(
            @Valid @RequestBody CreateLoanApplicationRequest request,
            @AuthenticationPrincipal UserDetails user) {

        Long dealerId = 1L; // TODO: extract from user context

        LoanApplicationResponse created = service.createApplication(request, dealerId);

        URI location = URI.create("/api/applications/" + created.getId());

        return ResponseEntity.created(location).body(created);
    }

    // ────────────────────────────────────────────────
    //  PUT /api/applications/{id}
    //  Full update (replace entire resource)
    // ────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody CreateLoanApplicationRequest request,
            @AuthenticationPrincipal UserDetails user) {

        // TODO: ownership check
        LoanApplicationResponse updated = service.updateApplication(id, request);

        return ResponseEntity.ok(updated);
    }

    // ────────────────────────────────────────────────
    //  DELETE /api/applications/{id}
    //  Delete application (soft or hard, depending on business)
    // ────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {

        // TODO: ownership check + business rules (cannot delete approved loans, etc.)
        service.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }
}