/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.partner.controller;

import com.microlibrary.partner.dto.PartnerRequest;
import com.microlibrary.partner.dto.PartnerResponse;
import com.microlibrary.partner.exception.BussinesRuleException;
import com.microlibrary.partner.service.PartnerService;
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
@Tag(name = "Partner API", description = "This API serves all functionality for management of Partners")
@RestController
@RequestMapping("/partner")
public class PartnerController {

    @Autowired
    PartnerService pas;

    @Operation(description = "FindAll Partners", summary = "Return 404 if no partners found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/list")
    public ResponseEntity<?> getAll() throws BussinesRuleException {

        List<PartnerResponse> findAll = pas.getAll();

        return ResponseEntity.ok(findAll);
    }

    @Operation(description = "Find Partner by ID", summary = "Return 404 if the partner not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) throws BussinesRuleException {

        PartnerResponse getById = pas.getById(id);

        return ResponseEntity.ok(getById);
    }

    @Operation(description = "Insert Partner", summary = "Return 400 if the business validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PostMapping("/post")
    public ResponseEntity<PartnerResponse> post(@RequestBody PartnerRequest input) throws BussinesRuleException {

        PartnerResponse post = pas.post(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @Operation(description = "Update Partner", summary = "Return 400 if the business validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PutMapping("/{id}")
    public ResponseEntity<PartnerResponse> put(@PathVariable(name = "id") long id, @RequestBody PartnerRequest input) throws BussinesRuleException {

        PartnerResponse put = pas.put(id, input);

        return ResponseEntity.ok(put);
    }

    @Operation(description = "Delete Partner", summary = "Return 404 if the partner not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @DeleteMapping("/{id}")
    public ResponseEntity<PartnerResponse> delete(@PathVariable(name = "id") long id) throws BussinesRuleException {

        PartnerResponse delete = pas.delete(id);

        return ResponseEntity.ok(delete);
    }
}
