package com.microlibrary.loanline.service;

import com.microlibrary.loanline.common.LoanlineRequestMapper; // Actualizado
import com.microlibrary.loanline.common.LoanlineResponseMapper; // Actualizado
import com.microlibrary.loanline.dto.LoanlineRequest; // Actualizado
import com.microlibrary.loanline.dto.LoanlineResponse; // Actualizado
import com.microlibrary.loanline.entities.Loanline; // Actualizado
import com.microlibrary.loanline.repository.LoanlineRepository; // Actualizado
import com.microlibrary.loanline.exception.BussinesRuleException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sergio
 */
@Service
public class LoanlineService { 

    @Autowired
    LoanlineRepository pr; 

    @Autowired
    LoanlineRequestMapper prq; 

    @Autowired
    LoanlineResponseMapper prp; 

    public List<LoanlineResponse> getAll() throws BussinesRuleException {

        List<Loanline> findAll = pr.findAll(); // Actualizado

        if (findAll.isEmpty()) {
            throw new BussinesRuleException("404", "Loanlines not found", HttpStatus.NOT_FOUND); 
        }

        List<LoanlineResponse> findAllResponse = prp.LoanlineListToLoanlineResponseList(findAll); 

        return findAllResponse;
    }

    public LoanlineResponse getById(long id) throws BussinesRuleException {

        Optional<Loanline> findById = pr.findById(id); // Actualizado

        if (!findById.isPresent()) {
            throw new BussinesRuleException("404", "Loanline not found", HttpStatus.NOT_FOUND); 
        }

        Loanline loanline = findById.get(); // Actualizado

        LoanlineResponse findIdResponse = prp.LoanlineToLoanlineResponse(loanline); 

        return findIdResponse;
    }

    public LoanlineResponse post(LoanlineRequest input) throws BussinesRuleException { 

        if (input == null || input.getProduct_Id() <= 0 || input.getLoan_Id() <= 0) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST);
        }

        Loanline post = prq.LoanlineRequestToLoanline(input); // Actualizado

        pr.save(post);

        LoanlineResponse loanlineResponse = prp.LoanlineToLoanlineResponse(post); 

        return loanlineResponse;
    }

    public LoanlineResponse put(long id, LoanlineRequest input) throws BussinesRuleException { 

        LoanlineResponse put = getById(id); // Actualizado

        if (put == null) {
            throw new BussinesRuleException("404", "Loanline not found", HttpStatus.NOT_FOUND); 
        }

        put.setProduct_Id(input.getProduct_Id());
        put.setLoan_Id(input.getLoan_Id());

        if (put.getProduct_Id() <= 0 || put.getLoan_Id() <= 0) {
            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST); 
        }

        Loanline loanline = prp.LoanlineResponseToLoanline(put); 

        pr.save(loanline);

        LoanlineResponse loanlineResponse = prp.LoanlineToLoanlineResponse(loanline); 

        return loanlineResponse;
    }

    public LoanlineResponse delete(long id) throws BussinesRuleException { 

        LoanlineResponse delete = getById(id); // Actualizado

        if (delete == null) {
            throw new BussinesRuleException("404", "Loanline not found", HttpStatus.NOT_FOUND); 
        }

        Loanline loanline = prp.LoanlineResponseToLoanline(delete);

        pr.delete(loanline);

        LoanlineResponse loanlineResponse = prp.LoanlineToLoanlineResponse(loanline); 

        return loanlineResponse;
    }
}
