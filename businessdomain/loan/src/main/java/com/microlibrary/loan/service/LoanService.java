package com.microlibrary.loan.service;

import com.microlibrary.loan.common.LoanRequestMapper;
import com.microlibrary.loan.common.LoanResponseMapper;
import com.microlibrary.loan.dto.LoanRequest;
import com.microlibrary.loan.dto.LoanResponse;
import com.microlibrary.loan.entities.Loan;
import com.microlibrary.loan.repository.LoanRepository;
import com.microlibrary.loan.exception.BussinesRuleException;
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
public class LoanService {

    @Autowired
    LoanRepository pr;

    @Autowired
    LoanRequestMapper prq;

    @Autowired
    LoanResponseMapper prp;

    public List<LoanResponse> getAll() throws BussinesRuleException {

        List<Loan> findAll = pr.findAll();

        if (findAll.isEmpty()) {
            throw new BussinesRuleException("404", "Loans not found", HttpStatus.NOT_FOUND);
        }

        List<LoanResponse> findAllResponse = prp.LoanListToLoanResponseList(findAll);

        return findAllResponse;
    }

    public LoanResponse getById(long id) throws BussinesRuleException {

        Optional<Loan> findById = pr.findById(id);

        if (!findById.isPresent()) {
            throw new BussinesRuleException("404", "Loan not found", HttpStatus.NOT_FOUND);
        }

        Loan loan = findById.get();

        LoanResponse findIdResponse = prp.LoanToLoanResponse(loan);

        return findIdResponse;
    }

    public LoanResponse post(LoanRequest input) throws BussinesRuleException {

        if (input == null || input.getStart_date() == null || input.getEnd_date() == null
                || input.getStatus().isEmpty()) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST);
        }

        Loan post = prq.LoanRequestToLoan(input);

        pr.save(post);

        LoanResponse loanResponse = prp.LoanToLoanResponse(post);

        return loanResponse;
    }

    public LoanResponse put(long id, LoanRequest input) throws BussinesRuleException {

        LoanResponse put = getById(id);

        if (put == null) {
            throw new BussinesRuleException("404", "Loan not found", HttpStatus.NOT_FOUND);
        }

        put.setStart_date(input.getStart_date());
        put.setEnd_date(input.getEnd_date());
        put.setStatus(input.getStatus());
        put.setCustomer_Id(input.getCustomer_Id());

        if (put.getStart_date().isEmpty() || put.getEnd_date().isEmpty() || put.getStatus().isEmpty()) {
            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.NOT_FOUND);
        }

        Loan loan = prp.LoanResponseToLoan(put);

        pr.save(loan);

        LoanResponse loanResponse = prp.LoanToLoanResponse(loan);

        return loanResponse;
    }

    public LoanResponse delete(long id) throws BussinesRuleException {

        LoanResponse delete = getById(id);

        if (delete == null) {
            throw new BussinesRuleException("404", "Loan not found", HttpStatus.NOT_FOUND);
        }

        Loan loan = prp.LoanResponseToLoan(delete);

        pr.delete(loan);

        LoanResponse loanResponse = prp.LoanToLoanResponse(loan);

        return loanResponse;
    }

}
