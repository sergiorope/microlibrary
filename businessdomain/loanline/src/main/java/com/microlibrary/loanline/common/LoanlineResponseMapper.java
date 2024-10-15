package com.microlibrary.loanline.common;

import com.microlibrary.loanline.dto.LoanlineResponse; // Actualizado
import com.microlibrary.loanline.entities.Loanline; // Actualizado
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author Sergio
 */
@Mapper(componentModel = "spring")
public interface LoanlineResponseMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "product_Id", target = "product_Id"),
        @Mapping(source = "loan_Id", target = "loan_Id"),
    })
    LoanlineResponse LoanlineToLoanlineResponse(Loanline source); // Actualizado

    List<LoanlineResponse> LoanlineListToLoanlineResponseList(List<Loanline> source); // Actualizado

    @InheritInverseConfiguration
    Loanline LoanlineResponseToLoanline(LoanlineResponse srr); // Actualizado

    @InheritInverseConfiguration
    List<Loanline> LoanlineResponseToLoanlineList(List<LoanlineResponse> source); // Actualizado
}
