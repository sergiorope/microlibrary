package com.microlibrary.loanline.service;

import com.microlibrary.loanline.dto.LoanlineRequest;
import com.microlibrary.loanline.dto.LoanlineResponse;
import com.microlibrary.loanline.exception.BussinesRuleException;
import java.net.UnknownHostException;

import java.util.List;

public interface LoanlineService {

    List<LoanlineResponse> getAll() throws BussinesRuleException;

    LoanlineResponse getById(long id) throws BussinesRuleException;

    List<LoanlineResponse> getByLoanId(long loanId) throws BussinesRuleException;

    String getProduct(long id) throws BussinesRuleException, UnknownHostException;

    LoanlineResponse post(LoanlineRequest input) throws BussinesRuleException;

    LoanlineResponse put(long id, LoanlineRequest input) throws BussinesRuleException;

    LoanlineResponse delete(long id) throws BussinesRuleException;
    
    String getProductName(long id) throws UnknownHostException;
}
