package com.dealerfinance.loan.mapper;

import org.mapstruct.Mapper;
import com.dealerfinance.loan.domain.LoanApplication;
import com.dealerfinance.loan.dto.LoanApplicationDTO;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {
    LoanApplicationDTO toDTO(LoanApplication entity);
    LoanApplication toEntity(LoanApplicationDTO dto);
}