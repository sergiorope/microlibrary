/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.customer.controller;

import com.microlibrary.customer.dto.CustomerRequest;
import com.microlibrary.customer.dto.CustomerResponse;
import com.microlibrary.customer.service.CustomerServiceImpl;
import com.microlibrary.customer.exception.BussinesRuleException;
import com.microlibrary.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.UnknownHostException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sergio
 */
@Tag(name = "Customer API", description = "This API serves all functionality for management Customers")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService cs;

    @Operation(description = "FindAll Customers", summary = "Return 404 if the customers not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/list")
    public ResponseEntity<?> getAll() throws BussinesRuleException {

        List<CustomerResponse> findAll = cs.getAll();

        return ResponseEntity.ok(findAll);

    }

    @Operation(description = "Find Customer by ID", summary = "Return 404 if the customer not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) throws BussinesRuleException {

        CustomerResponse getById = cs.getById(id);

        return ResponseEntity.ok(getById);

    }
    
    
    
    @Operation(description = "Find Customers by ID partner", summary = "Return 404 if the customer not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/by-partner/{id}")
    public ResponseEntity<?> getByPartnerId(@PathVariable(name = "id") long partner_Id) throws BussinesRuleException {

        List<CustomerResponse> getById = cs.getByPartnerId(partner_Id);

        return ResponseEntity.ok(getById);

    }



    @Operation(description = "Find partner name from ID of customer", summary = "Return 404 if the partner not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/partner/{id}")
    public ResponseEntity<?> getPartner(@PathVariable(name = "id") long id) throws BussinesRuleException, UnknownHostException {
        String partner = cs.getPartner(id);
        return ResponseEntity.ok(partner);
    }
    
    

    @Operation(description = "Insert Customer", summary = "Return 400 if the bussines validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PostMapping("/post")
    public ResponseEntity<CustomerResponse> post(@RequestBody CustomerRequest input) throws BussinesRuleException {

        CustomerResponse post = cs.post(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);

    }

    @Operation(description = "Update Customer", summary = "Return 400 if the bussines validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> put(@PathVariable(name = "id") long id, @RequestBody CustomerRequest input) throws BussinesRuleException {

        CustomerResponse put = cs.put(id, input);

        return ResponseEntity.ok(put);

    }

    @Operation(description = "Delete Customer", summary = "Return 404 if the customer not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable(name = "id") long id) throws BussinesRuleException {

        CustomerResponse delete = cs.delete(id);

        return ResponseEntity.ok(delete);

    }

}
