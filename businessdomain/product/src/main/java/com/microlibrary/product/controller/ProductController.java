/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.microlibrary.product.controller;

import com.microlibrary.product.dto.ProductRequest;
import com.microlibrary.product.dto.ProductResponse;
import com.microlibrary.product.entities.Product;
import com.microlibrary.product.service.ProductServiceImpl;
import com.microlibrary.product.exception.BussinesRuleException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@Tag(name = "Product API", description = "This APi serve all functionality for management Products")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl ps;

    @Operation(description = "FindAll Products", summary = "Return 404 if the products not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/list")
    public ResponseEntity<?> getAll() throws BussinesRuleException {

        List<ProductResponse> findAll = ps.getAll();

        return ResponseEntity.ok(findAll);

    }

    @Operation(description = "Find Product by ID", summary = "Return 404 if the product not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") long id) throws BussinesRuleException {

        ProductResponse getById = ps.getById(id);

        return ResponseEntity.ok(getById);

    }

    @Operation(description = "Insert Product", summary = "Return 400 if the bussines validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PostMapping("/post")
    public ResponseEntity<ProductResponse> post(@RequestBody ProductRequest input) throws BussinesRuleException {

        ProductResponse post = ps.post(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(post);

    }

    @Operation(description = "Update Product", summary = "Return 400 if the bussines validation is wrong")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> put(@PathVariable(name = "id") long id, @RequestBody ProductRequest input) throws BussinesRuleException {

        ProductResponse put = ps.put(id, input);

        return ResponseEntity.ok(put);

    }

    @Operation(description = "Delete Product", summary = "Return 404 if the product not found")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable(name = "id") long id) throws BussinesRuleException {

        ProductResponse delete = ps.delete(id);

        return ResponseEntity.ok(delete);

    }

}
