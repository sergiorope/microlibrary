/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.microlibrary.customer.service;

import com.microlibrary.customer.dto.CustomerRequest;
import com.microlibrary.customer.dto.CustomerResponse;
import com.microlibrary.customer.exception.BussinesRuleException;
import java.net.UnknownHostException;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Sergio
 */
public interface CustomerService {

    List<CustomerResponse> getAll() throws BussinesRuleException;

    CustomerResponse getById(long id) throws BussinesRuleException;

    List<CustomerResponse> getByPartnerId(long partner_Id) throws BussinesRuleException;

    String getPartner(long id) throws BussinesRuleException, UnknownHostException;

    CustomerResponse post(CustomerRequest input) throws BussinesRuleException;

    CustomerResponse put(long id, CustomerRequest input) throws BussinesRuleException;

    CustomerResponse delete(long id) throws BussinesRuleException;
    
    String getPartnerName(long id) throws UnknownHostException;

 

}
