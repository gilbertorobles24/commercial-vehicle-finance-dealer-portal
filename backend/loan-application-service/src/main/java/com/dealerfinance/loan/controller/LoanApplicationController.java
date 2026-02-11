package com.dealerfinance.loan.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import java.util.List;

import com.dealerfinance.loan.domain.LoanApplication;
import com.dealerfinance.loan.dto.LoanApplicationDTO;           // your DTO package
import com.dealerfinance.loan.repository.LoanApplicationRepository;
import com.dealerfinance.loan.mapper.LoanApplicationMapper;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationRepository repo;
    private final LoanApplicationMapper mapper; // MapStruct

    @GetMapping
    public List<LoanApplicationDTO> list(@AuthenticationPrincipal UserDetails user) {
        // filter by dealer later
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @PostMapping
    public LoanApplicationDTO create(@RequestBody LoanApplicationDTO dto, @AuthenticationPrincipal UserDetails user) {
        LoanApplication entity = mapper.toEntity(dto);
        // set dealer from user later
        entity.setStatus("DRAFT");
        return mapper.toDTO(repo.save(entity));
    }

    // Add GET /:id, PUT /:id, DELETE later
}