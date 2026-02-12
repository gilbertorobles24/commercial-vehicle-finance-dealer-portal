package com.dealerfinance.loan.interfaces.mapper;

import com.dealerfinance.loan.application.dto.request.CreateLoanApplicationRequest;
import com.dealerfinance.loan.application.dto.response.LoanApplicationResponse;
import com.dealerfinance.loan.domain.model.LoanApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dealerId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "amount", source = "amount")   // force this mapping
    @Mapping(target = "vehicleType", source = "vehicleType")
    @Mapping(target = "buyerName", source = "buyerName")
    @Mapping(target = "termMonths", source = "termMonths")
    LoanApplication toEntity(CreateLoanApplicationRequest request);

    LoanApplicationResponse toResponse(LoanApplication entity);
}