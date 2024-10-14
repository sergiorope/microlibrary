/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.customer.service;

import com.microlibrary.customer.common.CustomerRequestMapper;
import com.microlibrary.customer.common.CustomerResponseMapper;
import com.microlibrary.customer.dto.CustomerRequest;
import com.microlibrary.customer.dto.CustomerResponse;
import com.microlibrary.customer.entities.Customer;
import com.microlibrary.customer.exception.BussinesRuleException;
import com.microlibrary.customer.repository.CustomerRepository;
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
public class CustomerService {

    @Autowired
    CustomerRepository pr;

    @Autowired
    CustomerRequestMapper prq;

    @Autowired
    CustomerResponseMapper prp;

    public List<CustomerResponse> getAll() throws BussinesRuleException {

        List<Customer> findAll = pr.findAll();

        if (findAll.isEmpty()) {

            throw new BussinesRuleException("404", "customers not found", HttpStatus.NOT_FOUND);
        }

        List<CustomerResponse> findAllResponse = prp.CustomerListToCustomerResponseList(findAll);

        return findAllResponse;

    }

    public CustomerResponse getById(long id) throws BussinesRuleException {

        Optional<Customer> findById = pr.findById(id);

        if (!findById.isPresent()) {
            throw new BussinesRuleException("404", "customer not found", HttpStatus.NOT_FOUND);
        }

        Customer customer = findById.get();

        CustomerResponse findIdResponse = prp.CustomerToCustomerResponse(customer);

        return findIdResponse;
    }

    /*
    public CustomerResponse getPartner(long partner_Id) throws BussinesRuleException {

    
        return null;

    
    }

     */
    public CustomerResponse post(CustomerRequest input) throws BussinesRuleException {

        if (input == null || input.getName() == null || input.getSurname() == null) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.BAD_REQUEST);
        }

        Customer post = prq.CustomerRequestToCustomer(input);

        pr.save(post);

        CustomerResponse customerResponse = prp.CustomerToCustomerResponse(post);

        return customerResponse;
    }

    public CustomerResponse put(long id, CustomerRequest input) throws BussinesRuleException {

        CustomerResponse put = getById(id);

        if (put == null) {

            throw new BussinesRuleException("404", "customer not found", HttpStatus.NOT_FOUND);
        }

        put.setName(input.getName());
        put.setSurname(input.getSurname());
        put.setPartnerId(input.getPartnerId());

        if (put.getName().isEmpty() || put.getSurname().isEmpty()) {

            throw new BussinesRuleException("400", "Please complete the fields", HttpStatus.NOT_FOUND);
        }

        Customer customer = prp.CustomerResponseToCustomer(put);

        pr.save(customer);

        CustomerResponse customerResponse = prp.CustomerToCustomerResponse(customer);

        return customerResponse;
    }

    public CustomerResponse delete(long id) throws BussinesRuleException {

        CustomerResponse delete = getById(id);

        if (delete == null) {

            throw new BussinesRuleException("404", "customer not found", HttpStatus.NOT_FOUND);
        }

        Customer customer = prp.CustomerResponseToCustomer(delete);

        pr.delete(customer);

        CustomerResponse customerResponse = prp.CustomerToCustomerResponse(customer);

        return customerResponse;

    }

}
