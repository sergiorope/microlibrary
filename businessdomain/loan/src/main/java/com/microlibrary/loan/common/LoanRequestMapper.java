package com.microlibrary.loan.common;

import com.microlibrary.loan.dto.LoanRequest;
import com.microlibrary.loan.entities.Loan;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "start_date", target = "start_date"),
        @Mapping(source = "end_date", target = "end_date"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "customer_Id", target = "customer_Id")
    })
    Loan LoanRequestToLoan(LoanRequest source);

    List<Loan> LoanRequestListToLoanList(List<LoanRequest> source);

    @InheritInverseConfiguration
    LoanRequest LoanToLoanRequest(Loan source);

    @InheritInverseConfiguration
    List<LoanRequest> LoanListToLoanRequestList(List<Loan> source);
}
