package com.microlibrary.loanline.common;

import com.microlibrary.loanline.dto.LoanlineRequest; // Actualizado
import com.microlibrary.loanline.entities.Loanline; // Actualizado
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LoanlineRequestMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(source = "product_Id", target = "product_Id"),
        @Mapping(source = "loan_Id", target = "loan_Id"),
    })
    Loanline LoanlineRequestToLoanline(LoanlineRequest source); // Actualizado

    List<Loanline> LoanlineRequestListToLoanlineList(List<LoanlineRequest> source); // Actualizado

    @InheritInverseConfiguration
    LoanlineRequest LoanlineToLoanlineRequest(Loanline source); // Actualizado

    @InheritInverseConfiguration
    List<LoanlineRequest> LoanlineListToLoanlineRequestList(List<Loanline> source); // Actualizado
}
