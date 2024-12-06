/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.loan.controller;

import com.microlibrary.loan.dto.LoanRequest; // Asegúrate de que este DTO exista
import com.microlibrary.loan.dto.LoanResponse; // Asegúrate de que este DTO exista
import com.microlibrary.loan.entities.Loan; // Asegúrate de que esta entidad exista
import com.microlibrary.loan.service.LoanServiceImpl; // Asegúrate de que este servicio exista
import com.microlibrary.loan.exception.BussinesRuleException;
import com.microlibrary.loan.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Loan API", description = "This API serves all functionality for management Loans")
@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService ls; // Cambiado de ps a ls para seguir una convención

    @Operation(description = "Find All Loans", summary = "Return 404 if the loans are not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/list")
    public ResponseEntity<?> getAll() throws BussinesRuleException {

        List<LoanResponse> findAll = ls.getAll();

        return ResponseEntity.ok(findAll);
    }

    @Operation(description = "Find Loan by ID", summary = "Return 404 if the loan is not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) throws BussinesRuleException {

        LoanResponse getById = ls.getById(id);

        return ResponseEntity.ok(getById);
    }
    
    @Operation(description = "Find Loans by ID customer", summary = "Return 404 if the loan is not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/by-customer/{id}")
    public ResponseEntity<?> getByCustomerId(@PathVariable(name = "id") long customer_Id) throws BussinesRuleException {

        List<LoanResponse> getByCustomerId = ls.getByCustomerId(customer_Id);

        return ResponseEntity.ok(getByCustomerId);
    }
    
    @Operation(description = "Find customer name from ID of loan", summary = "Return 404 if the partner not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable(name = "id") long id) throws BussinesRuleException, UnknownHostException {
        String customer = ls.getCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @Operation(description = "Insert Loan", summary = "Return 400 if the business validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PostMapping("/post")
    public ResponseEntity<LoanResponse> post(@RequestBody LoanRequest input) throws BussinesRuleException {

        LoanResponse post = ls.post(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @Operation(description = "Update Loan", summary = "Return 400 if the business validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponse> put(@PathVariable(name = "id") long id, @RequestBody LoanRequest input) throws BussinesRuleException {

        LoanResponse put = ls.put(id, input);

        return ResponseEntity.ok(put);
    }

    @Operation(description = "Delete Loan", summary = "Return 404 if the loan is not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @DeleteMapping("/{id}")
    public ResponseEntity<LoanResponse> delete(@PathVariable(name = "id") long id) throws BussinesRuleException {

        LoanResponse delete = ls.delete(id);

        return ResponseEntity.ok(delete);
    }
}
