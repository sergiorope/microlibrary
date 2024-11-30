/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.microlibrary.loan.service;

import com.microlibrary.loan.dto.LoanRequest;
import com.microlibrary.loan.dto.LoanResponse;
import com.microlibrary.loan.exception.BussinesRuleException;
import java.net.UnknownHostException;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Sergio
 */
public interface LoanService {

    List<LoanResponse> getAll() throws BussinesRuleException;

    LoanResponse getById(long id) throws BussinesRuleException;

    String getCustomer(long id) throws BussinesRuleException, UnknownHostException;

    List<LoanResponse> getByCustomerId(long customer_Id) throws BussinesRuleException;

    LoanResponse post(LoanRequest input) throws BussinesRuleException;

    LoanResponse put(long id, LoanRequest input) throws BussinesRuleException;

    LoanResponse delete(long id) throws BussinesRuleException;

    String getCustomerName(long id) throws UnknownHostException;

}
