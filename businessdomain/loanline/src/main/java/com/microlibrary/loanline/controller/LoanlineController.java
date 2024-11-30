package com.microlibrary.loanline.controller;

import com.microlibrary.loanline.dto.LoanlineRequest; 
import com.microlibrary.loanline.dto.LoanlineResponse; 
import com.microlibrary.loanline.entities.Loanline; 
import com.microlibrary.loanline.service.LoanlineServiceImpl; 
import com.microlibrary.loanline.exception.BussinesRuleException; 
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

@Tag(name = "Loanline API", description = "This API serves all functionality for management of Loanlines")
@RestController
@RequestMapping("/loanline")
public class LoanlineController {

    @Autowired
    LoanlineServiceImpl lls;

    @Operation(description = "FindAll Loanlines", summary = "Return 404 if the loanlines not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/list")
    public ResponseEntity<?> getAll() throws BussinesRuleException {

        List<LoanlineResponse> findAll = lls.getAll();

        return ResponseEntity.ok(findAll);
    }

    @Operation(description = "Find Loanline by ID", summary = "Return 404 if the loanline not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) throws BussinesRuleException {

        LoanlineResponse getById = lls.getById(id);

        return ResponseEntity.ok(getById);
    }
    
    @Operation(description = "Find Loanlines by ID loan", summary = "Return 404 if the loanline not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/by-loan/{id}")
    public ResponseEntity<?> getByLoanId(@PathVariable(name = "id") long loan_Id) throws BussinesRuleException {

        List<LoanlineResponse> getById = lls.getByLoanId(loan_Id);

        return ResponseEntity.ok(getById);
    }
    
    
    @Operation(description = "Find product name from ID of loanline", summary = "Return 404 if the loanline not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "id") long id) throws BussinesRuleException, UnknownHostException {

        String productName = lls.getProduct(id);

        return ResponseEntity.ok(productName);
    }

    @Operation(description = "Insert Loanline", summary = "Return 400 if the business validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PostMapping("/post")
    public ResponseEntity<LoanlineResponse> post(@RequestBody LoanlineRequest input) throws BussinesRuleException {

        LoanlineResponse post = lls.post(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @Operation(description = "Update Loanline", summary = "Return 400 if the business validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PutMapping("/{id}")
    public ResponseEntity<LoanlineResponse> put(@PathVariable(name = "id") long id, @RequestBody LoanlineRequest input) throws BussinesRuleException {

        LoanlineResponse put = lls.put(id, input);

        return ResponseEntity.ok(put);
    }

    @Operation(description = "Delete Loanline", summary = "Return 404 if the loanline not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @DeleteMapping("/{id}")
    public ResponseEntity<LoanlineResponse> delete(@PathVariable(name = "id") long id) throws BussinesRuleException {

        LoanlineResponse delete = lls.delete(id);

        return ResponseEntity.ok(delete);
    }
}
