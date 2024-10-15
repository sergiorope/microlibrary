package com.microlibrary.loan.common;

import com.microlibrary.loan.dto.LoanResponse; // Asegúrate de que este DTO exista
import com.microlibrary.loan.entities.Loan; // Asegúrate de que esta entidad exista
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author sergio
 */
@Mapper(componentModel = "spring")
public interface LoanResponseMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "start_date", target = "start_date"),
        @Mapping(source = "end_date", target = "end_date"),
        @Mapping(source = "status", target = "status"),
        @Mapping(source = "customer_Id", target = "customer_Id")
    })
    LoanResponse LoanToLoanResponse(Loan source);

    List<LoanResponse> LoanListToLoanResponseList(List<Loan> source);

    @InheritInverseConfiguration
    Loan LoanResponseToLoan(LoanResponse srr);

    @InheritInverseConfiguration
    List<Loan> LoanResponseToLoanList(List<LoanResponse> source);
}
